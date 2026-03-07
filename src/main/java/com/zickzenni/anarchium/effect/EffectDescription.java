package com.zickzenni.anarchium.effect;

public class EffectDescription
{
    public final String id;

    public boolean tickable;

    public int ticks;

    public EffectDescription(String id)
    {
        this.id = id;
        this.tickable = false;
    }

    public EffectDescription(String id, int ticks)
    {
        this.id = id;
        this.tickable = true;
        this.ticks = ticks;
    }
}