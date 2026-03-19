package com.zickzenni.anarchium.client.network.handler;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class TimerTickPacketHandler
{
    public static void handle(final TimerTickPacket packet, final IPayloadContext ignoredCtx)
    {
        var instance = Anarchium.getClient();
        var diff = instance.getTimerTicks() - packet.ticks();

        /*
         * Correct prediction from the client.
         *
         * 11 because of 10 prediction ticks and +1
         */
        if (diff <= (packet.duration() - 11) && diff >= (packet.duration() - 11))
        {
            instance.setTimerTicks(packet.ticks());
        }

        instance.setTimerDuration(packet.duration());
        instance.predictTicks = 10;
    }
}
