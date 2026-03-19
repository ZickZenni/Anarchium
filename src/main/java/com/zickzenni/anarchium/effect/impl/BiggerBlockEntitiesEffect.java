package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class BiggerBlockEntitiesEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final ConfigValue<Double> SCALE_MULTIPLIER =
            ConfigValue.newDoubleInRange("scale_multiplier", 3.0, 0.0, 128.0);
    public static final EffectProperties<BiggerBlockEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(BiggerBlockEntitiesEffect.class)
                    .id("bigger_block_entities")
                    .supplier(BiggerBlockEntitiesEffect::new)
                    .config(DURATION)
                    .config(SCALE_MULTIPLIER)
                    .build();

    public static boolean ENABLED = false;

    public BiggerBlockEntitiesEffect()
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
