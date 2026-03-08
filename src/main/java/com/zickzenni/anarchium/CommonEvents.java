package com.zickzenni.anarchium;

import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
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
    }
}
