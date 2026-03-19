package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.ClientEffectManager;
import com.zickzenni.anarchium.network.packet.ActivateEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ActivateEffectPacketHandler
{
    private ActivateEffectPacketHandler() {}

    public static void handle(final ActivateEffectPacket packet, final IPayloadContext ignoredCtx)
    {
        var player = Minecraft.getInstance().player;

        if (player == null)
        {
            return;
        }

        var location = ResourceLocation.tryParse(packet.id());

        if (location == null)
        {
            Anarchium.LOGGER.error("Failed to parse effect location {}", packet.id());
            return;
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            if (effect.getLocation().equals(location))
            {
                player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
                Anarchium.getClient().resetTimer();
                return;
            }
        }

        var effect = ClientEffectManager.createEffect(location);

        if (effect != null)
        {
            player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
        }

        Anarchium.getClient().resetTimer();
    }
}
