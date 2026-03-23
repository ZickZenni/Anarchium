package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class BrokenWorldEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 25);
    public static final EffectProperties<BrokenWorldEffect> PROPERTIES =
            EffectProperties.Builder.of(BrokenWorldEffect.class)
                    .id("broken_world")
                    .supplier(BrokenWorldEffect::new)
                    .conflict(WhereAreMyChunksEffect.class)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public BrokenWorldEffect()
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
