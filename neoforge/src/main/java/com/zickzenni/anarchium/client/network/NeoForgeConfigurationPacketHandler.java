package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.ConfigurationPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class NeoForgeConfigurationPacketHandler
{
    private NeoForgeConfigurationPacketHandler()
    {
    }

    public static void handle(final ConfigurationPacket packet, final IPayloadContext ignoredCtx)
    {
        ConfigurationPacketHandler.handle(packet);
    }
}
