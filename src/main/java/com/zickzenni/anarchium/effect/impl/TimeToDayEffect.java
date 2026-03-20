package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;

public class TimeToDayEffect extends InstantEffect
{
    public static final EffectProperties<TimeToDayEffect> PROPERTIES =
            EffectProperties.Builder.of(TimeToDayEffect.class)
                    .id("time_to_day")
                    .supplier(TimeToDayEffect::new)
                    .build();

    public TimeToDayEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var level : Anarchium.getServer().getMinecraftServer().getAllLevels())
        {
            level.setDayTime(1000);
        }
    }
}
