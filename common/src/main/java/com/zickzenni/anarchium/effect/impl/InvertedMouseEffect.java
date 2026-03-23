package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class InvertedMouseEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 60);
    public static final EffectProperties<InvertedMouseEffect> PROPERTIES =
            EffectProperties.Builder.of(InvertedMouseEffect.class)
                    .id("inverted_mouse")
                    .supplier(InvertedMouseEffect::new)
                    .config(DURATION)
                    .build();

    public InvertedMouseEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static boolean ENABLED = false;

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
