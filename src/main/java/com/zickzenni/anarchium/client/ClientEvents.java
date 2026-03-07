package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Anarchium.MODID, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void onSetup(FMLClientSetupEvent event)
    {
    }
}
