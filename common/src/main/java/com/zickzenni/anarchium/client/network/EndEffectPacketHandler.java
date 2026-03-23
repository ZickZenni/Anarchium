package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.network.EndEffectPacket;
import net.minecraft.resources.ResourceLocation;

public final class EndEffectPacketHandler
{
    private EndEffectPacketHandler()
    {

    }

    public static void handle(final EndEffectPacket packet)
    {
        var location = ResourceLocation.tryParse(packet.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", packet.id());
            return;
        }

        ClientEffectManager.removeEffect(location);
    }
}
