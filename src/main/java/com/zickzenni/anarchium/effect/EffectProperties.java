package com.zickzenni.anarchium.effect;

/**
 * Represents the properties of an effect.
 */
public class EffectProperties
{
    private EffectType type;

    private int durationTicks;

    private EffectProperties()
    {
        this.type = EffectType.INSTANT;
        this.durationTicks = 0;
    }

    public EffectProperties duration(int ticks)
    {
        if (this.type == EffectType.INDEFINITE)
        {
            throw new IllegalStateException("Effect is already indefinite");
        }

        this.durationTicks = ticks;
        this.type = EffectType.TICKED;
        return this;
    }

    public EffectProperties indefinite()
    {
        if (this.durationTicks > 0)
        {
            throw new IllegalStateException("Effect is already tickable");
        }

        this.type = EffectType.INDEFINITE;
        return this;
    }

    public static EffectProperties of()
    {
        return new EffectProperties();
    }

    /**
     * Retrieves the type of the effect.
     */
    public EffectType getType()
    {
        return type;
    }

    /**
     * Retrieves the duration of the effect in ticks.
     */
    public int getDurationTicks()
    {
        return durationTicks;
    }
}