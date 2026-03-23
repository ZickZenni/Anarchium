package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;

public class TimeToDayEffect extends InstantEffect
{
    public static final EffectProperties<TimeToDayEffect> PROPERTIES =
            EffectProperties.Builder.of(TimeToDayEffect.class).id("time_to_day").supplier(TimeToDayEffect::new).build();

    public TimeToDayEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var level : Services.SERVER_PROVIDER.getServer().getAllLevels())
        {
            level.setDayTime(1000);
        }
    }
}
