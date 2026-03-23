package com.zickzenni.anarchium.registry;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.impl.*;
import com.zickzenni.anarchium.network.ConfigurationPacket;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.*;

public class EffectRegistry
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Marker LOADING = MarkerFactory.getMarker("LOADING");
    private static final Marker CONFIG = MarkerFactory.getMarker("CONFIG");

    private static final Map<ResourceLocation, EffectProperties<?>> REGISTRY = new HashMap<>();
    private static boolean frozen;

    /**
     * Registers all base effects of Anarchium.
     */
    public static void register()
    {
        if (frozen)
        {
            throw new IllegalStateException("Registry is already frozen.");
        }

        register(AntiPortraitEffect.PROPERTIES);
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
//        register(EnchantArmorPieceEffect.PROPERTIES);
//        register(EnchantCurrentItemEffect.PROPERTIES);
        register(EntityMagnetEffect.PROPERTIES);
        register(EveryoneIsAVillagerEffect.PROPERTIES);
        register(ExplodeNearbyEntitiesEvent.PROPERTIES);
        register(ExplodePlayersEffect.PROPERTIES);
        register(FakeTeleportToHeavenEffect.PROPERTIES);
        register(FatiguePotionEffect.PROPERTIES);
        register(FlatWorldEffect.PROPERTIES);
        register(FlingPlayersEffect.PROPERTIES);
        register(ForceJumpEffect.PROPERTIES);
        register(ForceSneakEffect.PROPERTIES);
        register(GetRotatedEffect.PROPERTIES);
        register(HighPitchEffect.PROPERTIES);
        register(InvertVelocityEffect.PROPERTIES);
        register(InvisibilityPotionEffect.PROPERTIES);
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
//        register(RotatingCameraEffect.PROPERTIES);
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
        register(GiveDiamondsEffect.PROPERTIES);
        register(DirtEffect.PROPERTIES);
        register(UpsideDownMobs.PROPERTIES);
        register(InsaneGravityEffect.PROPERTIES);
        register(FireworksEffect.PROPERTIES);
        register(ConsoleExperienceEffect.PROPERTIES);
        register(GTA2Effect.PROPERTIES);
        register(GoodbyeEffect.PROPERTIES);
        register(TimeToDayEffect.PROPERTIES);
        register(TimeToNightEffect.PROPERTIES);
        register(CreateNetherPortalEffect.PROPERTIES);
        register(PTSDEffect.PROPERTIES);
        register(DisableMobAIEffect.PROPERTIES);
        register(BunnyHopEffect.PROPERTIES);
        register(MissingCSSAssets.PROPERTIES);
        register(NoGUIEffect.PROPERTIES);
        register(InvertedMouseEffect.PROPERTIES);

        frozen = true;
        LOGGER.info("Finished registration with a total of {} entries", REGISTRY.size());
    }

    /**
     * Creates the configuration specs.
     */
    public static void createConfigSpecs(ConfigSpec builder)
    {
        if (!frozen)
        {
            throw new IllegalStateException("Cannot initialize configuration before the registry has been frozen.");
        }

        for (var property : REGISTRY.values())
        {
            for (var value : property.getConfig())
            {
                value.setPrefix("effects." + property.getId().toLanguageKey());
                value.configure(builder);
            }

            LOGGER.debug(
                    CONFIG,
                    "Configuring effect class {} of mod @{}",
                    property.getClazz().getCanonicalName(),
                    property.getId().getNamespace()
            );
        }

        LOGGER.info("Finished configuration of effects");
    }

    /**
     * Registers a new effect.
     */
    private static <T extends Effect> void register(EffectProperties<T> properties)
    {
        if (frozen)
        {
            throw new IllegalStateException(
                    "Cannot register new entries to EffectRegistry after the registry has been frozen.");
        }

        if (REGISTRY.containsKey(properties.getId()))
        {
            throw new IllegalArgumentException("Duplicate registration " + properties.getId());
        }

        REGISTRY.put(properties.getId(), properties);
        LOGGER.debug(
                LOADING,
                "Registering new effect class {} of mod @{}",
                properties.getClazz().getCanonicalName(),
                properties.getId().getNamespace()
        );
    }

    /**
     * Creates and returns a {@link ConfigurationPacket} containing the configuration data of all registered effects in the registry.
     *
     * @return a {@link ConfigurationPacket} containing the configuration details of the registered effects.
     */
    public static ConfigurationPacket createConfigurationPacket()
    {
        List<ConfigurationPacket.Effect> effects = new ArrayList<>();

        for (var property : REGISTRY.values())
        {
            if (property.getConfig().isEmpty())
            {
                continue;
            }

            List<ConfigurationPacket.Entry> entries = new ArrayList<>();

            for (ConfigValue<?> value : property.getConfig())
            {
                /*
                 * These are server-side only used.
                 */
                if (value.getName().equals("enabled") || value.getName().equals("weight"))
                {
                    continue;
                }

                entries.add(new ConfigurationPacket.Entry(value.getName(), value.getType(), value.getRaw()));
            }

            effects.add(new ConfigurationPacket.Effect(property.getId().toString(), entries));
        }

        return new ConfigurationPacket(effects);
    }

    /**
     * Retrieves the registered effects in the registry.
     */
    public static Map<ResourceLocation, EffectProperties<?>> getRegistry()
    {
        return Collections.unmodifiableMap(REGISTRY);
    }
}
