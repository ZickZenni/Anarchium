package com.zickzenni.anarchium.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public abstract class BaseEffect implements Effect
{
    protected final Identifier identifier;

    public BaseEffect(Identifier identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier()
    {
        return this.identifier;
    }

    @Override
    public String getGUIName()
    {
        return Component.translatable(getTranslationKey(this.identifier)).getString();
    }

    /**
     * Gets the translation key for the given identifier.
     */
    protected static String getTranslationKey(Identifier identifier)
    {
        return identifier.getNamespace() + "." + identifier.getPath();
    }
}
