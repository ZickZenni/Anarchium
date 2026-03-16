package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class NoSneakingEffect extends TimedEffect
{
    public static final EffectProperties<NoSneakingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoSneakingEffect.class)
                    .id("no_sneaking")
                    .supplier(NoSneakingEffect::new)
                    .conflict(ForceSneakEffect.class)
                    .build();

    public static boolean ENABLED = false;

    public NoSneakingEffect()
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
