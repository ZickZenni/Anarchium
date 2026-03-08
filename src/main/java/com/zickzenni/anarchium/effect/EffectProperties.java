package com.zickzenni.anarchium.effect;

public class EffectProperties
{
    private final int ticks;

    private EffectProperties(int ticks)
    {
        this.ticks = ticks;
    }

    public boolean isTickable()
    {
        return ticks > 0;
    }

    public int getTicks()
    {
        return ticks;
    }

    public static final EffectProperties REVERSED_GRAVITY = new EffectProperties(100);
}