package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class NoJumpingEffect extends TimedEffect
{
    public static final EffectProperties<NoJumpingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoJumpingEffect.class)
                    .id("no_jumping")
                    .supplier(NoJumpingEffect::new)
                    .conflict(ForceJumpEffect.class)
                    .build();

    public static boolean ENABLED = false;

    public NoJumpingEffect()
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
