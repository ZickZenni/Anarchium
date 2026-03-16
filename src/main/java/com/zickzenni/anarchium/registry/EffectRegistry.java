package com.zickzenni.anarchium.registry;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectSupplier;
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

    private static boolean frozen;

    public static void register()
    {
        registerEffect(ReversedGravityEffect.ID, ReversedGravityEffect.SUPPLIER);
        registerEffect(FlingPlayersEffect.ID, FlingPlayersEffect.SUPPLIER);
        registerEffect(FakeTeleportToHeavenEffect.ID, FakeTeleportToHeavenEffect.SUPPLIER);
        registerEffect(NegativeFieldOfViewEffect.ID, NegativeFieldOfViewEffect.SUPPLIER);
        registerEffect(SpawnCreepersEffect.ID, SpawnCreepersEffect.SUPPLIER);
        registerEffect(SpawnMinecartEffect.ID, SpawnMinecartEffect.SUPPLIER);
        registerEffect(BurnPlayersEffect.ID, BurnPlayersEffect.SUPPLIER);
        registerEffect(WideMobsEffect.ID, WideMobsEffect.SUPPLIER);
        registerEffect(LargeEntitiesEffect.ID, LargeEntitiesEffect.SUPPLIER);
        registerEffect(SpinningMobsEffect.ID, SpinningMobsEffect.SUPPLIER);
        registerEffect(ZeusEffect.ID, ZeusEffect.SUPPLIER);
        registerEffect(ForceJumpEffect.ID, ForceJumpEffect.SUPPLIER);
        registerEffect(ReplaceEverySoundWithVillagersEffect.ID, ReplaceEverySoundWithVillagersEffect.SUPPLIER);
        registerEffect(SuicideEffect.ID, SuicideEffect.SUPPLIER);
        registerEffect(TeleportToHeavenEffect.ID, TeleportToHeavenEffect.SUPPLIER);
        registerEffect(TeleportToVoidEffect.ID, TeleportToVoidEffect.SUPPLIER);
        registerEffect(SkeletonsHaveSpinbotEffect.ID, SkeletonsHaveSpinbotEffect.SUPPLIER);
        registerEffect(EveryoneIsAVillagerEffect.ID, EveryoneIsAVillagerEffect.SUPPLIER);
        registerEffect(PlaceLavaEffect.ID, PlaceLavaEffect.SUPPLIER);
        registerEffect(ExplodePlayersEffect.ID, ExplodePlayersEffect.SUPPLIER);
        registerEffect(ExplodeNearbyEntitiesEvent.ID, ExplodeNearbyEntitiesEvent.SUPPLIER);
        registerEffect(EntityMagnetEffect.ID, EntityMagnetEffect.SUPPLIER);
        registerEffect(TeleportNearbyMobsToPlayersEffect.ID, TeleportNearbyMobsToPlayersEffect.SUPPLIER);
        registerEffect(InvertVelocityEffect.ID, InvertVelocityEffect.SUPPLIER);
        registerEffect(SpawnBoatEffect.ID, SpawnBoatEffect.SUPPLIER);
        registerEffect(PlaceNearbyMobsIntoBoatEffect.ID, PlaceNearbyMobsIntoBoatEffect.SUPPLIER);
        registerEffect(JailEffect.ID, JailEffect.SUPPLIER);
        registerEffect(NoGravityEffect.ID, NoGravityEffect.SUPPLIER);
        registerEffect(QuakeFieldOfViewEffect.ID, QuakeFieldOfViewEffect.SUPPLIER);
        registerEffect(GetRotatedEffect.ID, GetRotatedEffect.SUPPLIER);
        registerEffect(PortraitEffect.ID, PortraitEffect.SUPPLIER);
        registerEffect(BlackScreenEffect.ID, BlackScreenEffect.SUPPLIER);
        registerEffect(SpawnWanderingTraderEffect.ID, SpawnWanderingTraderEffect.SUPPLIER);
        registerEffect(RotatingCameraEffect.ID, RotatingCameraEffect.SUPPLIER);
        registerEffect(RollingCameraEffect.ID, RollingCameraEffect.SUPPLIER);
        registerEffect(DropItemEffect.ID, DropItemEffect.SUPPLIER);
        registerEffect(DropInventoryEffect.ID, DropInventoryEffect.SUPPLIER);
        registerEffect(BlurryScreenEffect.ID, BlurryScreenEffect.SUPPLIER);
        registerEffect(DamagePlayersEffect.ID, DamagePlayersEffect.SUPPLIER);
        registerEffect(FatiguePotionEffect.ID, FatiguePotionEffect.SUPPLIER);
        registerEffect(BlindnessPotionEffect.ID, BlindnessPotionEffect.SUPPLIER);
        registerEffect(NoJumpingEffect.ID, NoJumpingEffect.SUPPLIER);
        registerEffect(NoSneakingEffect.ID, NoSneakingEffect.SUPPLIER);
        registerEffect(ForceSneakEffect.ID, ForceSneakEffect.SUPPLIER);
        registerEffect(WhereAreMyChunksEffect.ID, WhereAreMyChunksEffect.SUPPLIER);
        registerEffect(WhereIsTheSkyEffect.ID, WhereIsTheSkyEffect.SUPPLIER);
        registerEffect(BiggerBlockEntitiesEffect.ID, BiggerBlockEntitiesEffect.SUPPLIER);
        registerEffect(BurnNearbyMobsEffect.ID, BurnNearbyMobsEffect.SUPPLIER);
        registerEffect(HighPitchEffect.ID, HighPitchEffect.SUPPLIER);
        registerEffect(LowPitchEffect.ID, LowPitchEffect.SUPPLIER);
        registerEffect(BrokenWorldEffect.ID, BrokenWorldEffect.SUPPLIER);

        frozen = true;
        LOGGER.info("Registered {} effects", SUPPLIERS.size());
    }

    /**
     * Registers a new effect.
     */
    private static <T extends Effect> void registerEffect(ResourceLocation location, EffectSupplier<T> supplier)
    {
        if (frozen)
        {
            throw new IllegalStateException("Cannot register new entries to EffectRegistry after the registry has been frozen.");
        }

        if (SUPPLIERS.containsKey(location))
        {
            throw new IllegalArgumentException("Duplicate registration " + location);
        }

        SUPPLIERS.put(location, supplier);
        LOGGER.info("Registering new effect {} of mod @{}", location.getPath(), location.getNamespace());
    }

    public static Map<ResourceLocation, EffectSupplier<?>> getSuppliers()
    {
        return Collections.unmodifiableMap(SUPPLIERS);
    }
}
