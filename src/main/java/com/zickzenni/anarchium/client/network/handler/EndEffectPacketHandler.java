package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.ClientEffectManager;
import com.zickzenni.anarchium.network.packet.EndEffectPacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EndEffectPacketHandler
{
    public static void handle(final EndEffectPacket packet, final IPayloadContext ignoredCtx)
    {
        var location = ResourceLocation.tryParse(packet.id());

        if (location == null)
        {
            Anarchium.LOGGER.error("Failed to parse effect location {}", packet.id());
            return;
        }

        ClientEffectManager.removeEffect(location);
    }
}
