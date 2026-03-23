package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.network.TickEffectPacket;
import net.minecraft.resources.ResourceLocation;

public final class TickEffectPacketHandler
{
    private TickEffectPacketHandler()
    {

    }

    public static void handle(final TickEffectPacket packet)
    {
        var location = ResourceLocation.tryParse(packet.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", packet.id());
            return;
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            if (effect.getLocation().equals(location))
            {
                effect.setTicks(packet.ticks());
                break;
            }
        }
    }
}
