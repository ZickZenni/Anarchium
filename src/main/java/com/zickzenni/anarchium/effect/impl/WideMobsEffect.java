package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class WideMobsEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);

    public static final EffectProperties<WideMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(WideMobsEffect.class)
                    .id("wide_mobs")
                    .supplier(WideMobsEffect::new)
                    .config(DURATION)
                    .build();

    // ======================================================

    public WideMobsEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableWideLivingEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableWideLivingEntities = false;
    }
}
