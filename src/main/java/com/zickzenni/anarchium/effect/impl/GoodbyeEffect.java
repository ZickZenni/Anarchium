package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class GoodbyeEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 20);
    public static final EffectProperties<GoodbyeEffect> PROPERTIES =
            EffectProperties.Builder.of(GoodbyeEffect.class)
                    .id("goodbye")
                    .supplier(GoodbyeEffect::new)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public GoodbyeEffect()
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
