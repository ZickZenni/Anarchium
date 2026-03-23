package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import com.zickzenni.anarchium.network.ActivateEffectPacket;
import com.zickzenni.anarchium.network.EndEffectPacket;
import com.zickzenni.anarchium.network.TickEffectPacket;
import com.zickzenni.anarchium.network.TimerTickPacket;
import com.zickzenni.anarchium.platform.Services;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ServerEffectManager
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<Effect> EFFECTS = new ArrayList<>();

    static int timerTicks = Integer.MAX_VALUE;

    private ServerEffectManager()
    {
    }

    /**
     * Executes a tick update for all effects.
     */
    public static void tick(ILevelTickEvent<ServerLevel> event)
    {
        switch (event.getStage())
        {
            case PRE:
            {
                for (var effect : EFFECTS)
                {
                    effect.onLevelTickServer(event);
                }
                break;
            }
            case POST:
            {
                if (timerTicks > 0)
                {
                    runPostTick(event);
                    timerTicks--;
                } else
                {
                    timerTicks = AnarchiumServer.TIMER_DURATION.get();

                    var registry = EffectRegistry.getRegistry();
                    var entries = registry.values()
                            .stream()
                            .filter(EffectProperties::isEnabled)
                            .toArray(EffectProperties[]::new);
                    var property = getRandomEffect(entries);

                    if (property == null)
                    {
                        runPostTick(event);
                        LOGGER.error("Could not find a random effect!");
                        return;
                    }

                    activateEffect(property);
                    runPostTick(event);
                }

                /*
                 * Reduce network traffic.
                 */
                if (timerTicks % 5 == 0)
                {
                    Services.PACKET_DISTRIBUTOR.broadcast(new TimerTickPacket(
                            timerTicks,
                            AnarchiumServer.TIMER_DURATION.get()
                    ));
                }
                break;
            }
        }
    }

    public static EffectProperties<?> getRandomEffect(EffectProperties<?>[] entries)
    {
        var totalWeight = Arrays.stream(entries).mapToInt(EffectProperties::getWeight).sum();
        var random = new Random().nextInt(totalWeight);
        var weight = 0;

        for (var entry : entries)
        {
            weight += entry.getWeight();

            if (random < weight)
            {
                return entry;
            }
        }

        return null;
    }

    public static void activateEffect(EffectProperties<?> property)
    {
        var id = property.getId();

        for (var effect : EFFECTS)
        {
            if (effect.getLocation().equals(id) && effect.getDurationTicks() > 0)
            {
                LOGGER.debug("Reset active effect: {}", id);
                effect.setTicks(effect.getDurationTicks());
                Services.PACKET_DISTRIBUTOR.broadcast(new ActivateEffectPacket(id.toString()));
                return;
            }
        }

        var effect = property.getSupplier().create();
        effect.onStartServer();

        LOGGER.debug("Picked new effect: {}", id);
        EFFECTS.add(effect);
        Services.PACKET_DISTRIBUTOR.broadcast(new ActivateEffectPacket(id.toString()));
    }

    private static void runPostTick(ILevelTickEvent<ServerLevel> event)
    {
        for (var it = EFFECTS.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (!effect.hasEnded())
            {
                effect.onLevelTickServer(event);
                Services.PACKET_DISTRIBUTOR.broadcast(new TickEffectPacket(
                        effect.getLocation().toString(),
                        effect.getTicks()
                ));
            } else
            {
                it.remove();
                Services.PACKET_DISTRIBUTOR.broadcast(new EndEffectPacket(effect.getLocation().toString()));
            }
        }
    }
}
