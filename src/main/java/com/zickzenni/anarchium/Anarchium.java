package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.EffectIdentifiers;
import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.server.AnarchiumServer;
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
        EffectRegistry.registerDescription(EffectIdentifiers.REVERSED_GRAVITY, EffectProperties.REVERSED_GRAVITY);
        EffectRegistry.registerDescription(EffectIdentifiers.FAKE_TELEPORT_TO_HEAVEN, EffectProperties.FAKE_TELEPORT_TO_HEAVEN);

        AnarchiumServer.setup();
    }
}
