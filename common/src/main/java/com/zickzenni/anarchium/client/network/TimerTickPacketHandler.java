package com.zickzenni.anarchium.client.network;

import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.network.TimerTickPacket;

public final class TimerTickPacketHandler
{
    private TimerTickPacketHandler()
    {

    }

    public static void handle(final TimerTickPacket packet)
    {
        AnarchiumClient.getInstance().getTimer().synchronize(packet);
    }
}
