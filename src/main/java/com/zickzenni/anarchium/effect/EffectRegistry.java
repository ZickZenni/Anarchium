package com.zickzenni.anarchium.effect;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    public enum Side
    {
        CLIENT,
        SERVER
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    protected static Map<Side, Map<Identifier, Class<? extends IEffectHandler>>> HANDLERS = ImmutableMap.of(
            Side.CLIENT, new HashMap<>(),
            Side.SERVER, new HashMap<>()
    );

    protected static Map<Identifier, EffectProperties> DESCRIPTIONS = new HashMap<>();

    protected EffectRegistry()
    {
    }

    protected static void registerHandler(Identifier identifier, Class<? extends IEffectHandler> handler, Side side)
    {
        if (identifier == null)
        {
            throw new IllegalArgumentException("Identifier cannot be null");
        }

        if (handler == null)
        {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        var map = HANDLERS.get(side);

        if (map.containsKey(identifier))
        {
            throw new IllegalStateException("Duplicate effect identifier: " + identifier);
        }

        map.put(identifier, handler);
        LOGGER.info("[EffectRegistry] Registered effect '{}' for side {}", identifier, side);
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

    public static Map<Identifier, Class<? extends IEffectHandler>> getHandlers(Side side)
    {
        return HANDLERS.getOrDefault(side, new HashMap<>());
    }

    public static @Nullable Class<? extends IEffectHandler> getHandler(Identifier identifier, Side side)
    {
        var map = HANDLERS.getOrDefault(side, new HashMap<>());
        return map.getOrDefault(identifier, null);
    }

    public static @Nullable EffectProperties getDescription(Identifier identifier)
    {
        return DESCRIPTIONS.getOrDefault(identifier, null);
    }
}
