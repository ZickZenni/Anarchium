package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WideMobsEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<WideMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(WideMobsEffect.class)
                    .id("wide_mobs")
                    .supplier(WideMobsEffect::new)
                    .configure(WideMobsEffect::configure)
                    .build();

    // ======================================================

    public WideMobsEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableWideLivingEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableWideLivingEntities = false;
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 45);
    }
}
