package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class WhereIsTheSkyEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);

    public static final EffectProperties<WhereIsTheSkyEffect> PROPERTIES =
            EffectProperties.Builder.of(WhereIsTheSkyEffect.class)
                    .id("where_is_the_sky")
                    .supplier(WhereIsTheSkyEffect::new)
                    .config(DURATION)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public WhereIsTheSkyEffect()
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
