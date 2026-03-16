package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NoGravityEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<NoGravityEffect> PROPERTIES =
            EffectProperties.Builder.of(NoGravityEffect.class)
                    .id("no_gravity")
                    .supplier(NoGravityEffect::new)
                    .conflict(ReversedGravityEffect.class)
                    .configure(NoGravityEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public NoGravityEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartServer()
    {
        ENABLED = true;
    }

    @Override
    public void onEndServer()
    {
        ENABLED = false;
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
        DURATION = builder.define("duration", 20 * 40);
    }
}
