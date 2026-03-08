package com.zickzenni.anarchium.effect;

public class EffectDescription
{
    public boolean tickable;

    public int ticks;

    protected EffectDescription()
    {
        this.tickable = false;
    }

    protected EffectDescription(int ticks)
    {
        this.tickable = true;
        this.ticks = ticks;
    }

    public static final EffectDescription REVERSED_GRAVITY = new EffectDescription(100);
}