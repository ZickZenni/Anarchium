package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.network.packet.ConfigurationPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ConfigurationPacketHandler
{
    public static void handle(final ConfigurationPacket packet, final IPayloadContext ignoredCtx)
    {
        for (var effectEle : packet.effects())
        {
            var location = ResourceLocation.tryParse(effectEle.id());

            if (location == null)
            {
                continue;
            }

            var property = EffectRegistry.getRegistry().getOrDefault(location, null);

            if (property == null)
            {
                continue;
            }

            for (var entry : effectEle.entries())
            {
                for (var value : property.getConfig())
                {
                    if (value.getName().equals(entry.id()))
                    {
                        value.setRaw(entry.value());
                        break;
                    }
                }
            }
        }
    }
}
