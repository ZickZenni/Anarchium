package com.zickzenni.anarchium;

import com.zickzenni.anarchium.client.ClientPayloadHandler;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = Anarchium.MODID)
public class CommonEvents
{
    @SubscribeEvent
    public static void registerPayload(RegisterPayloadHandlersEvent event)
    {
        final var registrar = event.registrar("1");
        registrar.playToClient(ActivateEffectPacket.TYPE, ActivateEffectPacket.STREAM_CODEC, ClientPayloadHandler::handleActivateEffect);
        registrar.playToClient(EndEffectPacket.TYPE, EndEffectPacket.STREAM_CODEC, ClientPayloadHandler::handleEndEffect);
        registrar.playToClient(TickEffectPacket.TYPE, TickEffectPacket.STREAM_CODEC, ClientPayloadHandler::handleTickEffect);
        registrar.playToClient(TimerTickPacket.TYPE, TimerTickPacket.STREAM_CODEC, ClientPayloadHandler::handleTimerTick);
    }
}
