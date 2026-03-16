package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class LargeEntitiesEffect extends TimedEffect
{
    public static final EffectProperties<LargeEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(LargeEntitiesEffect.class)
                    .id("large_entities")
                    .supplier(LargeEntitiesEffect::new)
                    .build();

    public LargeEntitiesEffect()
    {
        super(PROPERTIES.getId(), 20 * 45);
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
