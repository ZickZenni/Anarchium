package com.zickzenni.anarchium.effect;

import org.jetbrains.annotations.Nullable;

public class EffectInstance
{
    public final Effect effect;

    public final @Nullable IEffectImpl impl;

    public int ticks;

    public EffectInstance(Effect effect, @Nullable IEffectImpl impl)
    {
        this.effect = effect;
        this.impl = impl;
        this.ticks = effect.getProperties().getDurationTicks();
    }
}
