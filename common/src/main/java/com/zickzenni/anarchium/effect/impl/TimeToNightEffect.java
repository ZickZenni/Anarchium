package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;

public class TimeToNightEffect extends InstantEffect
{
    public static final EffectProperties<TimeToNightEffect> PROPERTIES =
            EffectProperties.Builder.of(TimeToNightEffect.class)
                    .id("time_to_night")
                    .supplier(TimeToNightEffect::new)
                    .build();

    public TimeToNightEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var level : Services.SERVER_PROVIDER.getServer().getAllLevels())
        {
            level.setDayTime(14000);
        }
    }
}
