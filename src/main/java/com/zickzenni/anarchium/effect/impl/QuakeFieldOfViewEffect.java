package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class QuakeFieldOfViewEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Integer> FOV;

    public static final EffectProperties<QuakeFieldOfViewEffect> PROPERTIES =
            EffectProperties.Builder.of(QuakeFieldOfViewEffect.class)
                    .id("quake_field_of_view")
                    .supplier(QuakeFieldOfViewEffect::new)
                    .conflict(NegativeFieldOfViewEffect.class)
                    .configure(QuakeFieldOfViewEffect::configure)
                    .build();

    // ======================================================

    public QuakeFieldOfViewEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = FOV.get();
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 45);
        FOV = builder.define("fov", 130);
    }
}

