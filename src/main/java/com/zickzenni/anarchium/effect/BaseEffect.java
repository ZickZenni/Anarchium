package com.zickzenni.anarchium.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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

    /**
     * Gets the translation key for the given location.
     */
    protected static String getTranslationKey(ResourceLocation location)
    {
        return location.getNamespace() + "." + location.getPath();
    }
}
