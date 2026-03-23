package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.ActivateEffectPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class NeoForgeActivateEffectPacketHandler
{
    private NeoForgeActivateEffectPacketHandler()
    {
    }

    public static void handle(final ActivateEffectPacket packet, final IPayloadContext ignoredCtx)
    {
        ActivateEffectPacketHandler.handle(packet);
    }
}
