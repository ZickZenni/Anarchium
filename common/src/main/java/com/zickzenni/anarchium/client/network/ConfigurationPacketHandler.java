package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.ConfigurationPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class ConfigurationPacketHandler
{
    private ConfigurationPacketHandler()
    {

    }

    public static void handle(final ConfigurationPacket packet)
    {
        for (var effectEle : packet.effects())
        {
            var location = ResourceLocation.tryParse(effectEle.id());

            if (location == null)
            {
                Minecraft.getInstance().disconnect();
                continue;
            }

            var property = EffectRegistry.getRegistry().getOrDefault(location, null);

            if (property == null)
            {
                Minecraft.getInstance().disconnect();
                break;
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
