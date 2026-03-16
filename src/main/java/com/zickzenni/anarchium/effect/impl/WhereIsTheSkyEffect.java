package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class WhereIsTheSkyEffect extends TimedEffect
{
    public static final EffectProperties<WhereIsTheSkyEffect> PROPERTIES =
            EffectProperties.Builder.of(WhereIsTheSkyEffect.class)
                    .id("where_is_the_sky")
                    .supplier(WhereIsTheSkyEffect::new)
                    .build();

    public static boolean ENABLED = false;

    public WhereIsTheSkyEffect()
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
