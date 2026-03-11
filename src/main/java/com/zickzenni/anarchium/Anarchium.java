package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Anarchium.MODID)
public class Anarchium
{
    public static final String MODID = "anarchium";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Anarchium(IEventBus eventBus)
    {
        eventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        EffectRegistry.init();
    }
}
