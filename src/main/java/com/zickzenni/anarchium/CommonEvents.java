package com.zickzenni.anarchium;

import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Anarchium.MODID)
public class CommonEvents
{
    @SubscribeEvent
    public static void registerPayload(RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                ActivateEffectPacket.TYPE,
                ActivateEffectPacket.STREAM_CODEC
        );

        registrar.playToClient(
                EndEffectPacket.TYPE,
                EndEffectPacket.STREAM_CODEC
        );

        registrar.playToClient(
                TickEffectPacket.TYPE,
                TickEffectPacket.STREAM_CODEC
        );

        registrar.playToClient(
                TimerTickPacket.TYPE,
                TimerTickPacket.STREAM_CODEC
        );
    }
}
