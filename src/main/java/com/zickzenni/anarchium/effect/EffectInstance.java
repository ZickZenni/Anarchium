package com.zickzenni.anarchium.effect;

import net.minecraft.resources.Identifier;

public class EffectInstance
{
    public final Identifier identifier;

    public final IEffectHandler handler;

    public int ticks;

    public boolean indefinite;

    public EffectInstance(Identifier identifier, IEffectHandler handler)
    {
        this.identifier = identifier;
        this.handler = handler;
        this.ticks = 0;
    }
}
