package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectIdentifiers;
import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.server.effect.EffectInstance;
import com.zickzenni.anarchium.server.effect.ServerFakeTeleportToHeavenEffect;
import com.zickzenni.anarchium.server.effect.ServerReversedGravityEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnarchiumServer
{
    private static final AnarchiumServer INSTANCE = new AnarchiumServer();

    private static final Logger LOGGER = LogUtils.getLogger();

    private final List<EffectInstance> activeEffects;

    private int newEffectTicks;

    private AnarchiumServer()
    {
        this.activeEffects = new ArrayList<>();
        this.newEffectTicks = 20 * 15;
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
     * Processes a single level tick.
     */
    public void processLevelTick(ServerLevel level, LevelTickStage stage)
    {
        switch (stage)
        {
            case Pre:
                processPreLevelTick(level);
                break;
            case Post:
                processPostLevelTick(level);
                break;
        }
    }

    private void processPreLevelTick(ServerLevel level)
    {
        for (var effect : activeEffects)
        {
            if (effect.ticks > 0 || (effect.indefinite && !effect.handler.hasIndefiniteEnded()))
            {
                effect.handler.onLevelTick(level, LevelTickStage.Pre);
            }
        }
    }

    private void processPostLevelTick(ServerLevel level)
    {
        for (var it = activeEffects.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.ticks > 0)
            {
                effect.handler.onLevelTick(level, LevelTickStage.Post);
                effect.ticks--;
            } else if (effect.indefinite && !effect.handler.hasIndefiniteEnded())
            {
                effect.handler.onLevelTick(level, LevelTickStage.Post);
            } else
            {
                effect.handler.onEnd();
                it.remove();
            }
        }

        if (newEffectTicks <= 0)
        {
            newEffectTicks = 20 * 30;
            chooseRandomEffect();
        } else
        {
            newEffectTicks--;
        }
    }

    /**
     * Chooses a random effect to activate.
     */
    private void chooseRandomEffect()
    {
        var registry = EffectRegistry.getHandlers(EffectRegistry.Side.SERVER);
        var keys = registry.keySet().toArray();
        var identifier = (Identifier) keys[new Random().nextInt(keys.length)];

        var effect = registry.get(identifier);
        var description = EffectRegistry.getDescription(identifier);

        if (description == null)
        {
            LOGGER.error("[Anarchium] Failed to find effect description for {}", identifier);
            return;
        }

        LOGGER.info("[Anarchium] Picked new effect: {}", identifier);

        /*
         * Update timers when we already have the same effect running.
         */
        for (var activeEffect : activeEffects)
        {
            if (activeEffect.identifier.equals(identifier) && !activeEffect.indefinite)
            {
                activeEffect.ticks = description.getDurationTicks();
                PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(identifier.toString(), activeEffect.ticks));
                return;
            }
        }

        /*
         * Create a new instance of the effect.
         */
        try
        {
            var handler = effect.getConstructor().newInstance();

            if (description.isTickable() || description.isIndefinite())
            {
                var instance = new EffectInstance(identifier, handler);

                if (description.isTickable())
                {
                    instance.ticks = description.getDurationTicks();
                } else
                {
                    instance.indefinite = true;
                }

                activeEffects.add(instance);
            }

            handler.onStart();
            PacketDistributor.sendToAllPlayers(new ActivateEffectPacket(identifier.toString(), description.getDurationTicks()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e)
        {
            LOGGER.error("[Anarchium] Failed to create instance of effect {}: {}", identifier, e);
        }
    }

    /**
     * Retrieves the instance.
     */
    public static AnarchiumServer getInstance()
    {
        return INSTANCE;
    }
}
