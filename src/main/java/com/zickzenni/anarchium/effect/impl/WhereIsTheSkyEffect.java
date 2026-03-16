package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WhereIsTheSkyEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<WhereIsTheSkyEffect> PROPERTIES =
            EffectProperties.Builder.of(WhereIsTheSkyEffect.class)
                    .id("where_is_the_sky")
                    .supplier(WhereIsTheSkyEffect::new)
                    .configure(WhereIsTheSkyEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public WhereIsTheSkyEffect()
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
