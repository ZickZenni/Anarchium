package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WhereAreMyChunksEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<WhereAreMyChunksEffect> PROPERTIES =
            EffectProperties.Builder.of(WhereAreMyChunksEffect.class)
                    .id("where_are_my_chunks")
                    .supplier(WhereAreMyChunksEffect::new)
                    .conflict(BrokenWorldEffect.class)
                    .configure(WhereAreMyChunksEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public WhereAreMyChunksEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 35);
    }
}
