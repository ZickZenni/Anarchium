package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectRegistry;
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

    private static final List<Effect> EFFECTS = new ArrayList<>();

    private ClientEffectManager()
    {
    }

    /**
     * Executes a tick update for all effects.
     */
    public static void tick(ClientLevel level, LevelTickStage stage)
    {
        for (var effects : EFFECTS)
        {
            effects.onLevelTickClient(level, stage);
        }
    }

    /**
     * Creates a new effect instance.
     */
    public static void createEffect(Identifier identifier)
    {
        var supplier = EffectRegistry.getSuppliers().get(identifier);

        if (supplier == null)
        {
            LOGGER.error("Failed to get effect factory: {}", identifier);
            return;
        }

        var effect = supplier.create();
        effect.onStartClient();

        EFFECTS.add(effect);
    }

    public static void removeEffect(Identifier identifier)
    {
        for (var it = EFFECTS.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.getIdentifier().equals(identifier))
            {
                effect.onEndClient();
                it.remove();
                break;
            }
        }
    }

    public static void clear()
    {
        for (var effect : EFFECTS)
        {
            effect.onEndClient();
        }

        EFFECTS.clear();
    }

    public static List<Effect> getEffects()
    {
        return Collections.unmodifiableList(EFFECTS);
    }
}
