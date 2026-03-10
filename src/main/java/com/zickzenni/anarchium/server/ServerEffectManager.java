package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.*;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import com.zickzenni.anarchium.util.Environment;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerEffectManager
{
    private static final int TIMER_DURATION = 20 * 15;

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<EffectInstance> INSTANCES = new ArrayList<>();

    private static int timerTicks = TIMER_DURATION;

    private ServerEffectManager() {}

    /**
     * Executes a tick update for all effects.
     */
    public static void tick(ServerLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            for (var instance : INSTANCES)
            {
                if (instance.impl == null) {
                    continue;
                }

                if (instance.ticks > 0 || (instance.effect.getProperties().getType() == EffectType.INDEFINITE && !instance.impl.hasIndefiniteEnded()))
                {
                    instance.impl.onLevelTick(level, LevelTickStage.PRE);
                }
            }
        }

        if (stage == LevelTickStage.POST)
        {
            for (var it = INSTANCES.iterator(); it.hasNext(); )
            {
                var instance = it.next();

                if (instance.ticks > 0)
                {
                    PacketDistributor.sendToAllPlayers(new TickEffectPacket(instance.effect.getIdentifier().toString(), instance.ticks));

                    if (instance.impl != null)
                    {
                        instance.impl.onLevelTick(level, LevelTickStage.POST);
                    }

                    instance.ticks--;
                } else if (instance.effect.getProperties().getType() == EffectType.INDEFINITE && instance.impl != null && !instance.impl.hasIndefiniteEnded())
                {
                    instance.impl.onLevelTick(level, LevelTickStage.POST);
                } else
                {
                    if (instance.impl != null)
                    {
                        instance.impl.onEnd();
                    }

                    it.remove();
                    PacketDistributor.sendToAllPlayers(new EndEffectPacket(instance.effect.getIdentifier().toString()));
                }
            }

            if (timerTicks > 0)
            {
                timerTicks--;
            } else
            {
                timerTicks = TIMER_DURATION;

                int tries = 5;

                while (tries > 0)
                {
                    tries--;

                    var registry = EffectRegistry.getEffects();
                    var identifier = registry.keySet().toArray(Identifier[]::new)[new Random().nextInt(registry.size())];
                    var effect = registry.get(identifier);

                    var instance = createEffect(effect);

                    if (instance == null)
                    {
                        continue;
                    }

                    LOGGER.info("Picked new effect: {}", identifier);
                    PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(instance.effect.getIdentifier().toString(), instance.ticks));
                    return;
                }

                LOGGER.error("Failed to find a new effect after 5 tries");
            }

            /*
             * Reduce network traffic.
             */
            if (timerTicks % 5 == 0)
            {
                PacketDistributor.sendToAllPlayers(new TimerTickPacket(timerTicks, TIMER_DURATION));
            }
        }
    }

    /**
     * Creates a new effect instance.
     */
    public static EffectInstance createEffect(Effect effect)
    {
        /*
         * Update timers when we already have the same effect running.
         */
        for (var instance : INSTANCES)
        {
            if (!instance.effect.equals(effect))
            {
                continue;
            }

            /*
             * Indefinite effects do not have a "shared" timer, so we can't really reset it.
             * Probably implement this some time later.
             */
            if (instance.effect.getProperties().getType() == EffectType.INDEFINITE)
            {
                return null;
            }

            instance.ticks = instance.effect.getProperties().getDurationTicks();
            return instance;
        }

        var impl = effect.createImplInstance(Environment.SERVER);

        var instance = new EffectInstance(effect, impl);

        if (instance.impl != null)
        {
            instance.impl.onStart();
        }

        INSTANCES.add(instance);
        return instance;
    }
}
