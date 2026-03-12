package com.zickzenni.anarchium.effect;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.impl.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<ResourceLocation, EffectSupplier<?>> SUPPLIERS = new HashMap<>();

    public static void init()
    {
//        register(ReversedGravityEffect.ID, ReversedGravityEffect.SUPPLIER);
//        register(FlingPlayersEffect.ID, FlingPlayersEffect.SUPPLIER);
//        register(FakeTeleportToHeavenEffect.ID, FakeTeleportToHeavenEffect.SUPPLIER);
//        register(NegativeFieldOfViewEffect.ID, NegativeFieldOfViewEffect.SUPPLIER);
//        register(SpawnCreepersEffect.ID, SpawnCreepersEffect.SUPPLIER);
//        register(SpawnMinecartEffect.ID, SpawnMinecartEffect.SUPPLIER);
//        register(BurnPlayersEffect.ID, BurnPlayersEffect.SUPPLIER);
//        register(WideMobsEffect.ID, WideMobsEffect.SUPPLIER);
//        register(LargeEntitiesEffect.ID, LargeEntitiesEffect.SUPPLIER);
//        register(SpinningMobsEffect.ID, SpinningMobsEffect.SUPPLIER);
//        register(ZeusEffect.ID, ZeusEffect.SUPPLIER);
//        register(HoppingEffect.ID, HoppingEffect.SUPPLIER);
//        register(ReplaceEverySoundWithVillagersEffect.ID, ReplaceEverySoundWithVillagersEffect.SUPPLIER);
//        register(SuicideEffect.ID, SuicideEffect.SUPPLIER);
//        register(TeleportToHeavenEffect.ID, TeleportToHeavenEffect.SUPPLIER);
//        register(TeleportToVoidEffect.ID, TeleportToVoidEffect.SUPPLIER);
        register(SkeletonsHaveSpinbotEffect.ID, SkeletonsHaveSpinbotEffect.SUPPLIER);
    }

    /**
     * Registers a new effect.
     */
    private static <T extends Effect> void register(ResourceLocation location, EffectSupplier<T> factory)
    {
        if (SUPPLIERS.containsKey(location))
        {
            throw new IllegalStateException("Effect already registered: " + location);
        }

        SUPPLIERS.put(location, factory);
        LOGGER.info("Registering new effect: {}", location);
    }

    public static Map<ResourceLocation, EffectSupplier<?>> getSuppliers()
    {
        return Collections.unmodifiableMap(SUPPLIERS);
    }
}
