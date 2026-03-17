package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;

public class LargeEntitiesEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);

    public static final EffectProperties<LargeEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(LargeEntitiesEffect.class)
                    .id("large_entities")
                    .supplier(LargeEntitiesEffect::new)
                    .config(DURATION)
                    .build();

    // ======================================================

    public LargeEntitiesEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableLargeEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableLargeEntities = false;
    }
}
