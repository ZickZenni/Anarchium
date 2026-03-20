package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;

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
        for (var level : Anarchium.getServer().getMinecraftServer().getAllLevels())
        {
            level.setDayTime(14000);
        }
    }
}
