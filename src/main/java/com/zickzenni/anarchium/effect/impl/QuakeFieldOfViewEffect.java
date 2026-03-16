package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class QuakeFieldOfViewEffect extends TimedEffect
{
    public static final EffectProperties<QuakeFieldOfViewEffect> PROPERTIES =
            EffectProperties.Builder.of(QuakeFieldOfViewEffect.class)
                    .id("quake_field_of_view")
                    .supplier(QuakeFieldOfViewEffect::new)
                    .conflict(NegativeFieldOfViewEffect.class)
                    .build();

    public static final int FOV = 130;

    public QuakeFieldOfViewEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = FOV;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }
}

