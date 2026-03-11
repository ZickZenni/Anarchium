package com.zickzenni.anarchium;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AnarchiumSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Anarchium.MODID);

    public static final Holder<SoundEvent> DISPATCH_EFFECT_SOUND = SOUND_EVENTS.register("dispatch_effect", SoundEvent::createVariableRangeEvent);
}
