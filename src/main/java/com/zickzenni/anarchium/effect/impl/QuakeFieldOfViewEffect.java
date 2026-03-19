package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class QuakeFieldOfViewEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final ConfigValue<Integer> FOV = ConfigValue.newInteger("fov", 130);
    public static final EffectProperties<QuakeFieldOfViewEffect> PROPERTIES =
            EffectProperties.Builder.of(QuakeFieldOfViewEffect.class)
                    .id("quake_field_of_view")
                    .supplier(QuakeFieldOfViewEffect::new)
                    .conflict(NegativeFieldOfViewEffect.class)
                    .config(DURATION)
                    .config(FOV)
                    .build();

    public QuakeFieldOfViewEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = FOV.get();
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }
}

