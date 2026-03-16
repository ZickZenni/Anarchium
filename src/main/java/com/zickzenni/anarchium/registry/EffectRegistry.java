package com.zickzenni.anarchium.registry;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.impl.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Marker LOADING = MarkerFactory.getMarker("LOADING");

    private static final Marker CONFIG = MarkerFactory.getMarker("CONFIG");

    private static final Map<ResourceLocation, EffectProperties<?>> REGISTRY = new HashMap<>();

    private static ModConfigSpec SPECS;

    private static boolean frozen;

    public static void register()
    {
        register(BiggerBlockEntitiesEffect.PROPERTIES);
        register(BlackScreenEffect.PROPERTIES);
        register(BlindnessPotionEffect.PROPERTIES);
        register(BlurryScreenEffect.PROPERTIES);
        register(BrokenWorldEffect.PROPERTIES);
        register(BurnNearbyMobsEffect.PROPERTIES);
        register(BurnPlayersEffect.PROPERTIES);
        register(DamagePlayersEffect.PROPERTIES);
        register(DropInventoryEffect.PROPERTIES);
        register(DropItemEffect.PROPERTIES);
        register(EntityMagnetEffect.PROPERTIES);
        register(EveryoneIsAVillagerEffect.PROPERTIES);
        register(ExplodeNearbyEntitiesEvent.PROPERTIES);
        register(ExplodePlayersEffect.PROPERTIES);
        register(FakeTeleportToHeavenEffect.PROPERTIES);
        register(FatiguePotionEffect.PROPERTIES);
        register(FlingPlayersEffect.PROPERTIES);
        register(ForceJumpEffect.PROPERTIES);
        register(ForceSneakEffect.PROPERTIES);
        register(GetRotatedEffect.PROPERTIES);
        register(HighPitchEffect.PROPERTIES);
        register(InvertVelocityEffect.PROPERTIES);
        register(JailEffect.PROPERTIES);
        register(LargeEntitiesEffect.PROPERTIES);
        register(LowPitchEffect.PROPERTIES);
        register(NegativeFieldOfViewEffect.PROPERTIES);
        register(NoGravityEffect.PROPERTIES);
        register(NoJumpingEffect.PROPERTIES);
        register(NoSneakingEffect.PROPERTIES);
        register(PlaceLavaEffect.PROPERTIES);
        register(PlaceNearbyMobsIntoBoatEffect.PROPERTIES);
        register(PortraitEffect.PROPERTIES);
        register(QuakeFieldOfViewEffect.PROPERTIES);
        register(ReplaceEverySoundWithVillagersEffect.PROPERTIES);
        register(ReversedGravityEffect.PROPERTIES);
        register(RollingCameraEffect.PROPERTIES);
        register(RotatingCameraEffect.PROPERTIES);
        register(SkeletonsHaveSpinbotEffect.PROPERTIES);
        register(SpawnBoatEffect.PROPERTIES);
        register(SpawnCreepersEffect.PROPERTIES);
        register(SpawnMinecartEffect.PROPERTIES);
        register(SpawnWanderingTraderEffect.PROPERTIES);
        register(SpinningMobsEffect.PROPERTIES);
        register(SuicideEffect.PROPERTIES);
        register(TeleportNearbyMobsToPlayersEffect.PROPERTIES);
        register(TeleportToHeavenEffect.PROPERTIES);
        register(TeleportToVoidEffect.PROPERTIES);
        register(WhereAreMyChunksEffect.PROPERTIES);
        register(WhereIsTheSkyEffect.PROPERTIES);
        register(WideMobsEffect.PROPERTIES);
        register(ZeusEffect.PROPERTIES);

        frozen = true;
        LOGGER.info("Finished registration with a total of {} entries", REGISTRY.size());

        initConfiguration();
    }

    private static void initConfiguration()
    {
        if (!frozen)
        {
            throw new IllegalStateException("Cannot initialize configuration before the registry has been frozen.");
        }

        var builder = new ModConfigSpec.Builder();

        builder.push("effects");

        for (var property : REGISTRY.values())
        {
            builder.push(property.getId().toString());

            if (property.getConfigurer() != null)
            {
                property.getConfigurer().configure(builder);
            }

            builder.pop();

            LOGGER.debug(CONFIG, "Configuring effect class {} of mod @{}", property.getClazz()
                    .getCanonicalName(), property.getId().getNamespace());
        }

        builder.pop();

        SPECS = builder.build();
        LOGGER.info("Finished configuration of effects");
    }

    private static <T extends Effect> void register(EffectProperties<T> properties)
    {
        if (frozen)
        {
            throw new IllegalStateException("Cannot register new entries to EffectRegistry after the registry has been frozen.");
        }

        if (REGISTRY.containsKey(properties.getId()))
        {
            throw new IllegalArgumentException("Duplicate registration " + properties.getId());
        }

        REGISTRY.put(properties.getId(), properties);
        LOGGER.debug(LOADING, "Registering new effect class {} of mod @{}", properties.getClazz()
                .getCanonicalName(), properties.getId().getNamespace());
    }

    public static Map<ResourceLocation, EffectProperties<?>> getRegistry()
    {
        return Collections.unmodifiableMap(REGISTRY);
    }

    public static IConfigSpec getSpecs()
    {
        return SPECS;
    }
}
