package com.zickzenni.anarchium.effect.base;

import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.registry.SoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public abstract class BaseEffect implements Effect
{
    protected final ResourceLocation location;

    public BaseEffect(ResourceLocation location)
    {
        this.location = location;
    }

    @Override
    public ResourceLocation getLocation()
    {
        return this.location;
    }

    @Override
    public String getGUIName()
    {
        return Component.translatable(getTranslationKey(this.location)).getString();
    }

    @Override
    public Holder<SoundEvent> getDispatchSound()
    {
        return SoundRegistry.DISPATCH_EFFECT_SOUND;
    }

    /**
     * Gets the translation key for the given location.
     */
    protected static String getTranslationKey(ResourceLocation location)
    {
        return location.toLanguageKey();
    }
}
