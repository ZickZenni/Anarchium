package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class LargeEntitiesEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final ConfigValue<Double> SCALE_MULTIPLIER =
            ConfigValue.newDoubleInRange("scale_multiplier", 10.0, 0.0, 255.0);
    public static final EffectProperties<LargeEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(LargeEntitiesEffect.class)
                    .id("large_entities")
                    .supplier(LargeEntitiesEffect::new)
                    .config(DURATION)
                    .config(SCALE_MULTIPLIER)
                    .build();

    public static boolean ENABLED = false;

    public LargeEntitiesEffect()
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
