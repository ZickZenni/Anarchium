package com.zickzenni.anarchium.effect;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.util.Environment;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EffectManager
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private final Environment environment;

    public final List<EffectInstance> effects;

    public EffectManager(Environment environment)
    {
        this.environment = environment;
        this.effects = new ArrayList<>();
    }

    /**
     * Executes a tick update for all effects.
     */
    public void tick(Level level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            for (var effect : effects)
            {
                if (effect.ticks > 0 || (effect.indefinite && (!effect.handler.hasIndefiniteEnded() || this.environment == Environment.CLIENT)))
                {
                    effect.handler.onLevelTick(level, LevelTickStage.PRE);
                }
            }
        }

        if (stage == LevelTickStage.POST)
        {
            for (var it = effects.iterator(); it.hasNext(); )
            {
                var effect = it.next();

                /*
                 * The client should not end effects on its own.
                 */
                if (this.environment == Environment.CLIENT)
                {
                    effect.handler.onLevelTick(level, LevelTickStage.POST);
                    continue;
                }

                if (effect.ticks > 0)
                {
                    PacketDistributor.sendToAllPlayers(new TickEffectPacket(effect.identifier.toString(), effect.ticks));
                    effect.handler.onLevelTick(level, LevelTickStage.POST);
                    effect.ticks--;
                } else if (effect.indefinite && !effect.handler.hasIndefiniteEnded())
                {
                    effect.handler.onLevelTick(level, LevelTickStage.POST);
                } else
                {
                    effect.handler.onEnd();
                    it.remove();

                    PacketDistributor.sendToAllPlayers(new EndEffectPacket(effect.identifier.toString()));
                }
            }
        }
    }

    /**
     * Creates a new instance of an effect.
     */
    public @Nullable EffectInstance createEffect(Identifier identifier, Class<? extends IEffectHandler> handler, EffectProperties properties)
    {
        /*
         * Update timers when we already have the same effect running.
         */
        for (var activeEffect : this.effects)
        {
            if (!activeEffect.identifier.equals(identifier))
            {
                continue;
            }

            /*
             * Indefinite effects do not have a "shared" timer, so we can't really reset it.
             * Probably implement this some time later.
             */
            if (activeEffect.indefinite)
            {
                return null;
            }

            activeEffect.ticks = properties.getDurationTicks();
            return activeEffect;
        }

        /*
         * Create a new instance of the effect.
         */
        try
        {
            var instance = new EffectInstance(identifier, handler.getConstructor().newInstance(), properties);

            if (properties.isTickable() || properties.isIndefinite())
            {
                if (properties.isTickable())
                {
                    instance.ticks = properties.getDurationTicks();
                } else
                {
                    instance.indefinite = true;
                }

                this.effects.add(instance);
            }

            instance.handler.onStart();
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e)
        {
            LOGGER.error("Failed to create instance of effect {}: {}", identifier, e);
            return null;
        }
    }

    /**
     * Removes an active effect.
     */
    public void removeEffect(Identifier identifier)
    {
        for (var it = effects.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.identifier.equals(identifier))
            {
                effect.handler.onEnd();
                it.remove();
                break;
            }
        }
    }
}
