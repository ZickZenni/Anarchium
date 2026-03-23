package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.client.network.handler.*;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.registry.SoundRegistry;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

@Mod(Anarchium.MODID)
public class Anarchium
{
    public static final String MODID = "anarchium";
    public static final String NETWORK_VERSION = "1";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static Anarchium INSTANCE;
    private final AnarchiumClient client;
    private final AnarchiumServer server;

    public Anarchium(IEventBus bus, ModContainer container)
    {
        if (INSTANCE != null)
        {
            throw new IllegalStateException("Anarchium is already initialized!");
        }

        INSTANCE = this;

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            client = new AnarchiumClient();
        } else
        {
            client = null;
        }

        server = new AnarchiumServer();

        SoundRegistry.register(bus);
        EffectRegistry.register();

        container.registerConfig(ModConfig.Type.COMMON, createConfigSpecs());
    }

    /**
     * Creates the configuration specs for the mod.
     */
    private static ModConfigSpec createConfigSpecs()
    {
        var builder = new ModConfigSpec.Builder();

        EffectRegistry.createConfigSpecs(builder);

        builder.push("timer");
        AnarchiumServer.TIMER_DURATION.configure(builder);
        builder.pop();

        return builder.build();
    }

    /**
     * Creates a location using Anarchium's mod id as the namespace and a given path.
     */
    public static ResourceLocation getLocation(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    /**
     * Retrieves the instance of the mod.
     */
    public static Anarchium getInstance()
    {
        return INSTANCE;
    }

    /**
     * Retrieves the client instance.
     */
    public static AnarchiumClient getClient()
    {
        return INSTANCE.client;
    }

    /**
     * Retrieves the server instance.
     */
    public static AnarchiumServer getServer()
    {
        return INSTANCE.server;
    }

    @SuppressWarnings("unused")
    @EventBusSubscriber(modid = Anarchium.MODID)
    private static class CommonEvents
    {
        @SubscribeEvent
        public static void registerPayload(RegisterPayloadHandlersEvent event)
        {
            final var registrar = event.registrar(NETWORK_VERSION);
            registrar.playToClient(ActivateEffectPacket.TYPE, ActivateEffectPacket.STREAM_CODEC, ActivateEffectPacketHandler::handle);
            registrar.playToClient(EndEffectPacket.TYPE, EndEffectPacket.STREAM_CODEC, EndEffectPacketHandler::handle);
            registrar.playToClient(TickEffectPacket.TYPE, TickEffectPacket.STREAM_CODEC, TickEffectPacketHandler::handle);
            registrar.playToClient(TimerTickPacket.TYPE, TimerTickPacket.STREAM_CODEC, TimerTickPacketHandler::handle);
            registrar.playToClient(ConfigurationPacket.TYPE, ConfigurationPacket.STREAM_CODEC, ConfigurationPacketHandler::handle);
        }
    }
}
