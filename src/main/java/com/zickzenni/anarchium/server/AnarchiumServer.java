package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectIdentifiers;
import com.zickzenni.anarchium.effect.EffectManager;
import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import com.zickzenni.anarchium.server.effect.ServerFakeTeleportToHeavenEffect;
import com.zickzenni.anarchium.server.effect.ServerReversedGravityEffect;
import com.zickzenni.anarchium.util.Environment;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.Random;

public class AnarchiumServer
{
    private static final int TIMER_DURATION = 20 * 15;

    private static final AnarchiumServer INSTANCE = new AnarchiumServer();

    private static final Logger LOGGER = LogUtils.getLogger();

    private final EffectManager effectManager;

    private final Random random;

    private int timerTicks;

    private AnarchiumServer()
    {
        this.effectManager = new EffectManager();
        this.random = new Random();
        this.timerTicks = TIMER_DURATION;
    }

    public static void setup()
    {
        ServerEffectRegistry.registerHandler(
                EffectIdentifiers.REVERSED_GRAVITY,
                ServerReversedGravityEffect.class
        );
        ServerEffectRegistry.registerHandler(
                EffectIdentifiers.FAKE_TELEPORT_TO_HEAVEN,
                ServerFakeTeleportToHeavenEffect.class
        );
    }

    /**
     * Processes a level tick.
     */
    public void processLevelTick(ServerLevel level, LevelTickStage stage)
    {
        this.effectManager.tick(level, stage);

        if (stage == LevelTickStage.Post)
        {
            if (timerTicks <= 0)
            {
                timerTicks = TIMER_DURATION;
                chooseRandomEffect();
            } else
            {
                timerTicks--;
            }

            PacketDistributor.sendToAllPlayers(new TimerTickPacket(TIMER_DURATION - timerTicks, TIMER_DURATION));
        }
    }

    /**
     * Chooses a random effect to activate.
     */
    private void chooseRandomEffect()
    {
        int tries = 5;

        while (tries > 0)
        {
            tries--;

            var registry = EffectRegistry.getHandlers(Environment.SERVER);
            var identifier = registry.keySet().toArray(Identifier[]::new)[this.random.nextInt(registry.size())];
            var handler = registry.get(identifier);
            var properties = EffectRegistry.getDescription(identifier);

            if (properties == null)
            {
                continue;
            }

            var instance = this.effectManager.createEffect(identifier, handler, properties);

            if (instance == null)
            {
                continue;
            }

            LOGGER.info("[Anarchium] Picked new effect: {}", identifier);
            PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(identifier.toString(), instance.ticks));
            return;
        }

        LOGGER.error("[Anarchium] Failed to pick a new effect. Tried 5 times.");
    }

    /**
     * Retrieves the instance.
     */
    public static AnarchiumServer getInstance()
    {
        return INSTANCE;
    }
}
