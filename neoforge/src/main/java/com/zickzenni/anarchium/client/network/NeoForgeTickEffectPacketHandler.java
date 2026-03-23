package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.TickEffectPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class NeoForgeTickEffectPacketHandler
{
    private NeoForgeTickEffectPacketHandler()
    {
    }

    public static void handle(final TickEffectPacket packet, final IPayloadContext ignoredCtx)
    {
        TickEffectPacketHandler.handle(packet);
    }
}
