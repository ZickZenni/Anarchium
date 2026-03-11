package com.zickzenni.anarchium.effect;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.impl.*;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<Identifier, EffectSupplier<?>> SUPPLIERS = new HashMap<>();

    public static void init()
    {
        register(ReversedGravityEffect.ID, ReversedGravityEffect.SUPPLIER);
        register(FlingPlayersEffect.ID, FlingPlayersEffect.SUPPLIER);
        register(FakeTeleportToHeavenEffect.ID, FakeTeleportToHeavenEffect.SUPPLIER);
        register(InvertedFieldOfViewEffect.ID, InvertedFieldOfViewEffect.SUPPLIER);
        register(SpawnCreepersEffect.ID, SpawnCreepersEffect.SUPPLIER);
        register(SpawnMinecartEffect.ID, SpawnMinecartEffect.SUPPLIER);
        register(BurnPlayersEffect.ID, BurnPlayersEffect.SUPPLIER);
        register(WideMobsEffect.ID, WideMobsEffect.SUPPLIER);
        register(LargeEntitiesEffect.ID, LargeEntitiesEffect.SUPPLIER);
    }

    /**
     * Registers a new effect.
     */
    private static <T extends Effect> void register(Identifier identifier, EffectSupplier<T> factory)
    {
        if (SUPPLIERS.containsKey(identifier))
        {
            throw new IllegalStateException("Effect already registered: " + identifier);
        }

        SUPPLIERS.put(identifier, factory);
        LOGGER.info("Registering new effect: {}", identifier);
    }

    public static Map<Identifier, EffectSupplier<?>> getSuppliers()
    {
        return Collections.unmodifiableMap(SUPPLIERS);
    }
}
