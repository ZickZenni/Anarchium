package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class TimerTickPacketHandler
{
    public static void handle(final TimerTickPacket packet, final IPayloadContext ignoredCtx)
    {
        Anarchium.getClient().getTimer().synchronize(packet);
    }
}
