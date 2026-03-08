package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Anarchium.MODID, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void onSetup(final FMLClientSetupEvent event)
    {
        AnarchiumClient.setup();
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterClientPayloadHandlersEvent event)
    {
        event.register(
                ActivateEffectPacket.TYPE,
                ClientPayloadHandler::handleActivateEffect
        );
    }

    @SubscribeEvent
    public static void onPreLevelTick(LevelTickEvent.Pre event)
    {
        var level = event.getLevel();

        if (!level.isClientSide())
        {
            return;
        }

        AnarchiumClient.getInstance().processLevelTick((ClientLevel) level, LevelTickStage.Pre);
    }

    @SubscribeEvent
    public static void onPostLevelTick(LevelTickEvent.Post event)
    {
        var level = event.getLevel();

        if (!level.isClientSide())
        {
            return;
        }

        AnarchiumClient.getInstance().processLevelTick((ClientLevel) level, LevelTickStage.Post);
    }
}
