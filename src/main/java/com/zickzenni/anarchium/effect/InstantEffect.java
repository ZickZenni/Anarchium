package com.zickzenni.anarchium.effect;

import net.minecraft.resources.Identifier;

public class InstantEffect extends BaseEffect
{
    public InstantEffect(Identifier identifier)
    {
        super(identifier);
    }

    @Override
    public boolean hasEnded()
    {
        return true;
    }

    @Override
    public int getTicks()
    {
        return 0;
    }

    @Override
    public int getDurationTicks()
    {
        return 0;
    }
}
