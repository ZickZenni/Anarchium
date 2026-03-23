package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.network.ActivateEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class ActivateEffectPacketHandler
{
    private ActivateEffectPacketHandler()
    {
    }

    public static void handle(final ActivateEffectPacket packet)
    {
        var player = Minecraft.getInstance().player;

        if (player == null)
        {
            return;
        }

        var location = ResourceLocation.tryParse(packet.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", packet.id());
            Minecraft.getInstance().disconnect();
            return;
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            if (effect.getLocation().equals(location))
            {
                player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
                AnarchiumClient.getInstance().getTimer().reset();
                return;
            }
        }

        var effect = ClientEffectManager.createEffect(location);

        if (effect != null)
        {
            player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
        }

        AnarchiumClient.getInstance().getTimer().reset();
    }
}
