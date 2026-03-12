package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Anarchium.MODID)
public class Anarchium
{
    public static final String MODID = "anarchium";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Anarchium(IEventBus bus)
    {
        AnarchiumSounds.SOUND_EVENTS.register(bus);
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        EffectRegistry.init();
    }

    /**
     * Creates a location using Anarchium's mod id as the namespace and a given path.
     */
    public static ResourceLocation location(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
