package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BrokenWorldEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<BrokenWorldEffect> PROPERTIES =
            EffectProperties.Builder.of(BrokenWorldEffect.class)
                    .id("broken_world")
                    .supplier(BrokenWorldEffect::new)
                    .conflict(WhereAreMyChunksEffect.class)
                    .configure(BrokenWorldEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public BrokenWorldEffect()
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
        DURATION = builder.define("duration", 20 * 25);
    }
}
