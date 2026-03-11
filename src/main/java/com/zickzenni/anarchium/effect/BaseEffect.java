package com.zickzenni.anarchium.effect;

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
}
