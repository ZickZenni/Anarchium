package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NoJumpingEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<NoJumpingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoJumpingEffect.class)
                    .id("no_jumping")
                    .supplier(NoJumpingEffect::new)
                    .conflict(ForceJumpEffect.class)
                    .configure(NoJumpingEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public NoJumpingEffect()
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
