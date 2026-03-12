package com.zickzenni.anarchium.effect;

import net.minecraft.resources.ResourceLocation;

public class InstantEffect extends BaseEffect
{
    public InstantEffect(ResourceLocation location)
    {
        super(location);
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
