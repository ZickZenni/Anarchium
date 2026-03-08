package com.zickzenni.anarchium.effect;

/**
 * Represents the properties of an effect.
 */
public class EffectProperties
{
    private int durationTicks;

    private boolean indefinite;

    private EffectProperties() {}

    private EffectProperties duration(int ticks)
    {
        if (this.indefinite)
        {
            throw new IllegalStateException("Effect is already indefinite");
        }

        this.durationTicks = ticks;
        return this;
    }

    private EffectProperties indefinite()
    {
        if (this.durationTicks > 0)
        {
            throw new IllegalStateException("Effect is already tickable");
        }

        this.indefinite = true;
        return this;
    }

    /**
     * Determines if the effect is tickable.
     */
    public boolean isTickable()
    {
        return durationTicks > 0;
    }

    /**
     * Retrieves the duration of the effect in ticks.
     */
    public int getDurationTicks()
    {
        return durationTicks;
    }

    /**
     * Determines if the effect is running indefinite.
     */
    public boolean isIndefinite()
    {
        return indefinite;
    }

    /*
     * ======================================
     */

    public static final EffectProperties REVERSED_GRAVITY = new EffectProperties().duration(100);

    public static final EffectProperties FAKE_TELEPORT_TO_HEAVEN = new EffectProperties().indefinite();
}