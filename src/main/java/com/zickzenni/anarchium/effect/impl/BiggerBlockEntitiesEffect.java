package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class BiggerBlockEntitiesEffect extends TimedEffect
{
    public static final EffectProperties<BiggerBlockEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(BiggerBlockEntitiesEffect.class)
                    .id("bigger_block_entities")
                    .supplier(BiggerBlockEntitiesEffect::new)
                    .build();

    public static final float SCALE_MULTIPLIER = 3.0f;

    public static boolean ENABLED = false;

    public BiggerBlockEntitiesEffect()
    {
        super(PROPERTIES.getId(), 20 * 45);
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
