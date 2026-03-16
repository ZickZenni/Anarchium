package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BiggerBlockEntitiesEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Double> SCALE_MULTIPLIER;

    public static final EffectProperties<BiggerBlockEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(BiggerBlockEntitiesEffect.class)
                    .id("bigger_block_entities")
                    .supplier(BiggerBlockEntitiesEffect::new)
                    .configure(BiggerBlockEntitiesEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public BiggerBlockEntitiesEffect()
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
        DURATION = builder.define("duration", 20 * 45);
        SCALE_MULTIPLIER = builder.defineInRange("scale_multiplier", 3.0, 0.0, 128.0);
    }
}
