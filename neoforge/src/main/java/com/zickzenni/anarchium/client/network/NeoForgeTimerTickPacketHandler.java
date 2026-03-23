package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.network.TimerTickPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class NeoForgeTimerTickPacketHandler
{
    private NeoForgeTimerTickPacketHandler()
    {

    }

    public static void handle(final TimerTickPacket packet, final IPayloadContext ignoredCtx)
    {
        TimerTickPacketHandler.handle(packet);
    }
}
