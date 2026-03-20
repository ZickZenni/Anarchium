package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class NoSneakingEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);
    public static final EffectProperties<NoSneakingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoSneakingEffect.class)
                    .id("no_sneaking")
                    .supplier(NoSneakingEffect::new)
                    .conflict(ForceSneakEffect.class)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public NoSneakingEffect()
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
