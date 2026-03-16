package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class ForceSneakEffect extends TimedEffect
{
    public static final EffectProperties<ForceSneakEffect> PROPERTIES =
            EffectProperties.Builder.of(ForceSneakEffect.class)
                    .id("force_sneak")
                    .supplier(ForceSneakEffect::new)
                    .conflict(NoGravityEffect.class)
                    .build();

    public static boolean ENABLED = false;

    public ForceSneakEffect()
    {
        super(PROPERTIES.getId(), 20 * 35);
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
