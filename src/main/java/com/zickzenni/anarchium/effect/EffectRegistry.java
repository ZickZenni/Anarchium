package com.zickzenni.anarchium.effect;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.util.Environment;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    protected static Map<Environment, Map<Identifier, Class<? extends IEffectHandler>>> HANDLERS = ImmutableMap.of(
            Environment.CLIENT, new HashMap<>(),
            Environment.SERVER, new HashMap<>()
    );

    protected static Map<Identifier, EffectProperties> DESCRIPTIONS = new HashMap<>();

    protected EffectRegistry()
    {
    }

    protected static void registerHandler(Identifier identifier, Class<? extends IEffectHandler> handler, Environment environment)
    {
        if (identifier == null)
        {
            throw new IllegalArgumentException("Identifier cannot be null");
        }

        if (handler == null)
        {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        var map = HANDLERS.get(environment);

        if (map.containsKey(identifier))
        {
            throw new IllegalStateException("Duplicate effect identifier: " + identifier);
        }

        map.put(identifier, handler);
        LOGGER.info("[EffectRegistry] Registered effect '{}' for environment {}", identifier, environment);
    }

    public static void registerDescription(Identifier identifier, EffectProperties description)
    {
        if (identifier == null)
        {
            throw new IllegalArgumentException("Identifier cannot be null");
        }

        if (description == null)
        {
            throw new IllegalArgumentException("Description cannot be null");
        }

        if (DESCRIPTIONS.containsKey(identifier))
        {
            throw new IllegalStateException("Duplicate effect identifier: " + identifier);
        }

        DESCRIPTIONS.put(identifier, description);
        LOGGER.info("[EffectRegistry] Registered effect description: {}", identifier);
    }

    public static Map<Identifier, Class<? extends IEffectHandler>> getHandlers(Environment environment)
    {
        return HANDLERS.getOrDefault(environment, new HashMap<>());
    }

    public static @Nullable Class<? extends IEffectHandler> getHandler(Identifier identifier, Environment environment)
    {
        var map = HANDLERS.getOrDefault(environment, new HashMap<>());
        return map.getOrDefault(identifier, null);
    }

    public static @Nullable EffectProperties getDescription(Identifier identifier)
    {
        return DESCRIPTIONS.getOrDefault(identifier, null);
    }
}
