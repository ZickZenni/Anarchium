package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class NoJumpingEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);
    public static final EffectProperties<NoJumpingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoJumpingEffect.class)
                    .id("no_jumping")
                    .supplier(NoJumpingEffect::new)
                    .conflict(ForceJumpEffect.class)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public NoJumpingEffect()
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
