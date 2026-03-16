package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ForceSneakEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<ForceSneakEffect> PROPERTIES =
            EffectProperties.Builder.of(ForceSneakEffect.class)
                    .id("force_sneak")
                    .supplier(ForceSneakEffect::new)
                    .conflict(NoGravityEffect.class)
                    .configure(ForceSneakEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public ForceSneakEffect()
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
        DURATION = builder.define("duration", 20 * 30);
    }
}
