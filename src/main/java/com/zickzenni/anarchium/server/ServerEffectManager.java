package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.*;
import com.zickzenni.anarchium.network.packet.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packet.EndEffectPacket;
import com.zickzenni.anarchium.network.packet.TickEffectPacket;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.ResourceLocation;
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

    private static final List<Effect> EFFECTS = new ArrayList<>();

    private static int timerTicks = TIMER_DURATION;

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
            for (var it = EFFECTS.iterator(); it.hasNext(); )
            {
                var effect = it.next();

                if (!effect.hasEnded())
                {
                    effect.onLevelTickServer(level, stage);
                    PacketDistributor.sendToAllPlayers(new TickEffectPacket(effect.getLocation().toString(), effect.getTicks()));
                } else
                {
                    it.remove();
                    PacketDistributor.sendToAllPlayers(new EndEffectPacket(effect.getLocation().toString()));
                }
            }

            if (timerTicks > 0)
            {
                timerTicks--;
            } else
            {
                timerTicks = TIMER_DURATION;

                var registry = EffectRegistry.getSuppliers();
                var location = registry.keySet().toArray(ResourceLocation[]::new)[new Random().nextInt(registry.size())];
                var supplier = registry.get(location);

                for (var effect : EFFECTS)
                {
                    if (effect.getLocation().equals(location) && effect.getDurationTicks() > 0)
                    {
                        LOGGER.info("Reset active effect: {}", location);
                        effect.setTicks(effect.getDurationTicks());
                        PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(location.toString()));
                        return;
                    }
                }

                var effect = supplier.create();
                effect.onStartServer();

                LOGGER.info("Picked new effect: {}", location);
                EFFECTS.add(effect);
                PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(location.toString()));
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
}
