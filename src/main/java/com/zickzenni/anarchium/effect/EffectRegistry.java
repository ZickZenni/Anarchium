package com.zickzenni.anarchium.effect;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.Anarchium;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<Identifier, Effect> EFFECTS = new HashMap<>();

    /**
     * Registers a new effect.
     */
    public static @NotNull Effect register(String id, @NotNull EffectProperties properties)
    {
        var identifier = Identifier.fromNamespaceAndPath(Anarchium.MODID, id);

        if (EFFECTS.containsKey(identifier))
        {
            throw new IllegalStateException("Effect already registered: " + identifier);
        }

        var effect = new Effect(identifier, properties);
        EFFECTS.put(identifier, effect);
        LOGGER.info("Registering new effect: {}", identifier);

        return effect;
    }

    /**
     * Retrieves an effect by its identifier.
     */
    public static @Nullable Effect get(Identifier identifier)
    {
        return EFFECTS.getOrDefault(identifier, null);
    }

    /**
     * Retrieves all registered effects.
     */
    public static ImmutableMap<Identifier, Effect> getEffects()
    {
        return ImmutableMap.copyOf(EFFECTS);
    }
}
