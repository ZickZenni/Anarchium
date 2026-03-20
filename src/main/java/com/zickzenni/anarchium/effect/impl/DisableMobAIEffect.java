package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class DisableMobAIEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 60);
    public static final EffectProperties<DisableMobAIEffect> PROPERTIES =
            EffectProperties.Builder.of(DisableMobAIEffect.class)
                    .id("disable_mob_ai")
                    .supplier(DisableMobAIEffect::new)
                    .config(DURATION)
                    .build();

    public DisableMobAIEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static boolean ENABLED = false;

    @Override
    public void onStartServer()
    {
        ENABLED = true;
    }

    @Override
    public void onEndServer()
    {
        ENABLED = false;
    }
}
