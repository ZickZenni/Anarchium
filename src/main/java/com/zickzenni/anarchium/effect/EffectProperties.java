package com.zickzenni.anarchium.effect;

/**
 * Represents the properties of an effect.
 */
public class EffectProperties
{
    private final int durationTicks;

    private EffectProperties(int ticks)
    {
        this.durationTicks = ticks;
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

    /*
     * ======================================
     */

    public static final EffectProperties REVERSED_GRAVITY = new EffectProperties(100);
}