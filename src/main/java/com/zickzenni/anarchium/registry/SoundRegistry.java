package com.zickzenni.anarchium.registry;

import com.zickzenni.anarchium.Anarchium;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry
{
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Anarchium.MODID);

    public static final Holder<SoundEvent> DISPATCH_EFFECT_SOUND = SOUND_EVENTS.register("dispatch_effect", SoundEvent::createVariableRangeEvent);

    public static final Holder<SoundEvent> ZEUS_EFFECT_SOUND = SOUND_EVENTS.register("zeus_effect", SoundEvent::createVariableRangeEvent);

    public static void register(final IEventBus bus)
    {
        SOUND_EVENTS.register(bus);
    }
}
