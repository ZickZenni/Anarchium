package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class NoGravityEffect extends TimedEffect
{
    public static final EffectProperties<NoGravityEffect> PROPERTIES =
            EffectProperties.Builder.of(NoGravityEffect.class)
                    .id("no_gravity")
                    .supplier(NoGravityEffect::new)
                    .conflict(ReversedGravityEffect.class)
                    .build();

    public static boolean ENABLED = false;

    public NoGravityEffect()
    {
        super(PROPERTIES.getId(), 20 * 40);
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
