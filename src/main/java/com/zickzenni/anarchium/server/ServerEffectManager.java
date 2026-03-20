package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.network.packet.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packet.EndEffectPacket;
import com.zickzenni.anarchium.network.packet.TickEffectPacket;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
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
    public static void tick(ServerLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            for (var effect : EFFECTS)
            {
                effect.onLevelTickServer(level, stage);
            }
        }

        if (stage == LevelTickStage.POST)
        {
            if (timerTicks > 0)
            {
                runPostTick(level);
                timerTicks--;
            } else
            {
                timerTicks = AnarchiumServer.TIMER_DURATION.get();

                var registry = EffectRegistry.getRegistry();
                var entries = registry.values().stream()
                        .filter(EffectProperties::isEnabled)
                        .toArray(EffectProperties[]::new);
                var property = getRandomEffect(entries);

                if (property == null)
                {
                    runPostTick(level);
                    LOGGER.error("Could not find a random effect!");
                    return;
                }

                activateEffect(property);
                runPostTick(level);
            }

            /*
             * Reduce network traffic.
             */
            if (timerTicks % 5 == 0)
            {
                PacketDistributor.sendToAllPlayers(new TimerTickPacket(timerTicks, AnarchiumServer.TIMER_DURATION.get()));
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
                PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(id.toString()));
                return;
            }
        }

        var effect = property.getSupplier().create();
        effect.onStartServer();

        LOGGER.debug("Picked new effect: {}", id);
        EFFECTS.add(effect);
        PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(id.toString()));
    }

    private static void runPostTick(ServerLevel level)
    {
        for (var it = EFFECTS.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (!effect.hasEnded())
            {
                effect.onLevelTickServer(level, LevelTickStage.POST);
                PacketDistributor.sendToAllPlayers(new TickEffectPacket(effect.getLocation()
                        .toString(), effect.getTicks()));
            } else
            {
                it.remove();
                PacketDistributor.sendToAllPlayers(new EndEffectPacket(effect.getLocation().toString()));
            }
        }
    }
}
