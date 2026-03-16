package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NoSneakingEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<NoSneakingEffect> PROPERTIES =
            EffectProperties.Builder.of(NoSneakingEffect.class)
                    .id("no_sneaking")
                    .supplier(NoSneakingEffect::new)
                    .conflict(ForceSneakEffect.class)
                    .configure(NoSneakingEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public NoSneakingEffect()
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
