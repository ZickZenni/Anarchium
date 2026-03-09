package com.zickzenni.anarchium.effect;

import net.minecraft.resources.Identifier;

public class EffectInstance
{
    public final Identifier identifier;

    public final IEffectHandler handler;

    public final EffectProperties properties;

    public int ticks;

    public boolean indefinite;

    public EffectInstance(Identifier identifier, IEffectHandler handler, EffectProperties properties)
    {
        this.identifier = identifier;
        this.handler = handler;
        this.properties = properties;
        this.ticks = 0;
    }
}
