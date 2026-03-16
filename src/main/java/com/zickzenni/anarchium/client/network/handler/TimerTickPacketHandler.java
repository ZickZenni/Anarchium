package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.client.AnarchiumClient;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class TimerTickPacketHandler
{
    public static void handle(final TimerTickPacket packet, final IPayloadContext ignoredCtx)
    {
        var instance = AnarchiumClient.getInstance();
        var diff = instance.timerTicks - packet.ticks();

        /*
         * Correct prediction from the client.
         *
         * 11 because of 10 prediction ticks and +1
         */
        if (diff <= (packet.duration() - 11) && diff >= (packet.duration() - 11))
        {
            instance.timerTicks = packet.ticks();
        }

        instance.timerDuration = packet.duration();
        instance.predictTicks = 10;
    }
}
