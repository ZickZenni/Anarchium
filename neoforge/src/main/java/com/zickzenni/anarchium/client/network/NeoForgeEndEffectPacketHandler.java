package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.EndEffectPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class NeoForgeEndEffectPacketHandler
{
    private NeoForgeEndEffectPacketHandler()
    {
    }

    public static void handle(final EndEffectPacket packet, final IPayloadContext ignoredCtx)
    {
        EndEffectPacketHandler.handle(packet);
    }
}
