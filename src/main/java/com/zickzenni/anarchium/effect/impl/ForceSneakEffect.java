package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class ForceSneakEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final EffectProperties<ForceSneakEffect> PROPERTIES =
            EffectProperties.Builder.of(ForceSneakEffect.class)
                    .id("force_sneak")
                    .supplier(ForceSneakEffect::new)
                    .conflict(NoGravityEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public ForceSneakEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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
