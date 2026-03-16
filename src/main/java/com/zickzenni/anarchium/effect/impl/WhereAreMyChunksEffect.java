package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class WhereAreMyChunksEffect extends TimedEffect
{
    public static final EffectProperties<WhereAreMyChunksEffect> PROPERTIES =
            EffectProperties.Builder.of(WhereAreMyChunksEffect.class)
                    .id("where_are_my_chunks")
                    .supplier(WhereAreMyChunksEffect::new)
                    .conflict(BrokenWorldEffect.class)
                    .build();

    public static boolean ENABLED = false;

    public WhereAreMyChunksEffect()
    {
        super(PROPERTIES.getId(), 20 * 35);
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
    }
}
