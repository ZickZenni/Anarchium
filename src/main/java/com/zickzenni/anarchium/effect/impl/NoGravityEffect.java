package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class NoGravityEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 40);

    public static final EffectProperties<NoGravityEffect> PROPERTIES =
            EffectProperties.Builder.of(NoGravityEffect.class)
                    .id("no_gravity")
                    .supplier(NoGravityEffect::new)
                    .conflict(ReversedGravityEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public NoGravityEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartServer()
    {
        ENABLED = true;
    }

    @Override
    public void onEndServer()
    {
        ENABLED = false;
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
    }
}
