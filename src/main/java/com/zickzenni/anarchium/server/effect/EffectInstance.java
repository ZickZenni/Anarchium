package com.zickzenni.anarchium.server.effect;

import com.zickzenni.anarchium.effect.IEffectHandler;
import net.minecraft.resources.Identifier;

public class EffectInstance
{
    public final Identifier identifier;

    public final IEffectHandler handler;

    public int ticks;

    public EffectInstance(Identifier identifier, IEffectHandler handler)
    {
        this.identifier = identifier;
        this.handler = handler;
        this.ticks = 0;
    }
}
