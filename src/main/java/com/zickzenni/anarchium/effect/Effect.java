package com.zickzenni.anarchium.effect;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.effect.impl.ClientReversedGravityEffect;
import com.zickzenni.anarchium.server.effect.impl.ServerFakeTeleportToHeavenEffect;
import com.zickzenni.anarchium.server.effect.impl.ServerFlingPlayersEffect;
import com.zickzenni.anarchium.server.effect.impl.ServerReversedGravityEffect;
import com.zickzenni.anarchium.util.Environment;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Effect
{
    private static final Logger LOGGER = LogUtils.getLogger();

    // ======================================================

    public static final Effect REVERSED_GRAVITY = EffectRegistry.register("reversed_gravity",
                    EffectProperties.of().duration(200))
            .registerImpl(Environment.CLIENT, ClientReversedGravityEffect.class)
            .registerImpl(Environment.SERVER, ServerReversedGravityEffect.class);

    public static final Effect FLING_PLAYERS = EffectRegistry.register("fling_players",
                    EffectProperties.of())
            .registerImpl(Environment.SERVER, ServerFlingPlayersEffect.class);

    public static final Effect FAKE_TELEPORT_TO_HEAVEN = EffectRegistry.register("fake_teleport_to_heaven",
                    EffectProperties.of().indefinite())
            .registerImpl(Environment.SERVER, ServerFakeTeleportToHeavenEffect.class);

    public static void init() {}

    // ======================================================

    private final Identifier identifier;

    private final EffectProperties properties;

    private final Map<Environment, Class<? extends IEffectImpl>> impls;

    Effect(Identifier identifier, EffectProperties properties)
    {
        this.identifier = identifier;
        this.properties = properties;
        this.impls = new HashMap<>();
    }

    /**
     * Registers an implementation for the effect.
     * Dedicated servers ignore client implementations.
     */
    public Effect registerImpl(Environment environment, Class<? extends IEffectImpl> handler)
    {
        if (FMLEnvironment.getDist() == Dist.DEDICATED_SERVER && environment == Environment.CLIENT)
        {
            LOGGER.warn("Skipping registration of a client implementation for effect '{}' on a dedicated server ({})", identifier, handler);
            return this;
        }

        if (this.impls.containsKey(environment))
        {
            throw new IllegalStateException("Handler already registered for environment: " + environment);
        }

        this.impls.put(environment, handler);
        LOGGER.info("Registering implementation ({}) for effect '{}': {}", identifier.toString(), environment, handler);
        return this;
    }

    public @Nullable IEffectImpl createImplInstance(Environment environment)
    {
        try
        {
            var impl = this.getImplementation(environment);

            if (impl == null)
            {
                return null;
            }

            return impl.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e)
        {
            LOGGER.error("Failed to create instance of effect {}: {}", this.getIdentifier(), e);
            return null;
        }
    }

    /**
     * Retrieves the identifier of the effect.
     */
    public Identifier getIdentifier()
    {
        return identifier;
    }

    /**
     * Retrieves the properties of the effect.
     */
    public EffectProperties getProperties() {return properties;}

    /**
     * Retrieves the implementations of the effect.
     */
    public ImmutableMap<Environment, Class<? extends IEffectImpl>> getImplementations()
    {
        return ImmutableMap.copyOf(impls);
    }

    /**
     * Retrieves the implementation of the effect for a specific environment.
     */
    public @Nullable Class<? extends IEffectImpl> getImplementation(Environment environment)
    {
        return impls.getOrDefault(environment, null);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Effect effect = (Effect) o;
        return Objects.equals(identifier, effect.identifier);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(identifier);
    }
}
