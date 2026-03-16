package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.network.handler.ActivateEffectPacketHandler;
import com.zickzenni.anarchium.client.network.handler.EndEffectPacketHandler;
import com.zickzenni.anarchium.client.network.handler.TickEffectPacketHandler;
import com.zickzenni.anarchium.client.network.handler.TimerTickPacketHandler;
import com.zickzenni.anarchium.network.packet.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packet.EndEffectPacket;
import com.zickzenni.anarchium.network.packet.TickEffectPacket;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.registry.SoundRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

@Mod(Anarchium.MODID)
public class Anarchium
{
    public static final String MODID = "anarchium";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Anarchium(IEventBus bus, ModContainer container)
    {
        SoundRegistry.register(bus);
        EffectRegistry.register();

        container.registerConfig(ModConfig.Type.COMMON, EffectRegistry.getSpecs());
    }

    /**
     * Creates a location using Anarchium's mod id as the namespace and a given path.
     */
    public static ResourceLocation location(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @EventBusSubscriber(modid = Anarchium.MODID)
    private static class Events
    {
        @SubscribeEvent
        public static void registerPayload(RegisterPayloadHandlersEvent event)
        {
            final var registrar = event.registrar("1");
            registrar.playToClient(ActivateEffectPacket.TYPE, ActivateEffectPacket.STREAM_CODEC, ActivateEffectPacketHandler::handle);
            registrar.playToClient(EndEffectPacket.TYPE, EndEffectPacket.STREAM_CODEC, EndEffectPacketHandler::handle);
            registrar.playToClient(TickEffectPacket.TYPE, TickEffectPacket.STREAM_CODEC, TickEffectPacketHandler::handle);
            registrar.playToClient(TimerTickPacket.TYPE, TimerTickPacket.STREAM_CODEC, TimerTickPacketHandler::handle);
        }
    }
}
