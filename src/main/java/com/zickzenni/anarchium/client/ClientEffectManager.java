package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectInstance;
import com.zickzenni.anarchium.effect.EffectType;
import com.zickzenni.anarchium.util.Environment;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientEffectManager
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<EffectInstance> INSTANCES = new ArrayList<>();

    private ClientEffectManager() {}

    /**
     * Executes a tick update for all effects.
     */
    public static void tick(ClientLevel level, LevelTickStage stage)
    {
        for (var instance : INSTANCES)
        {
            if (instance.impl != null)
            {
                instance.impl.onLevelTick(level, stage);
            }
        }
    }

    /**
     * Creates a new effect instance.
     */
    public static void createEffect(Effect effect)
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
                return;
            }

            instance.ticks = instance.effect.getProperties().getDurationTicks();
            return;
        }

        var impl = effect.createImplInstance(Environment.CLIENT);
        var instance = new EffectInstance(effect, impl);

        if (instance.impl != null)
        {
            instance.impl.onStart();
        }

        INSTANCES.add(instance);
    }

    public static void removeEffect(Identifier identifier)
    {
        for (var it = INSTANCES.iterator(); it.hasNext(); )
        {
            var instance = it.next();

            if (instance.effect.getIdentifier().equals(identifier))
            {
                if (instance.impl != null)
                {
                    instance.impl.onEnd();
                }

                it.remove();
            }
        }
    }

    public static List<EffectInstance> getInstances()
    {
        return Collections.unmodifiableList(INSTANCES);
    }
}
