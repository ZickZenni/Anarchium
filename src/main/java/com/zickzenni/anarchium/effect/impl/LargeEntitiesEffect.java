package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class LargeEntitiesEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<LargeEntitiesEffect> PROPERTIES =
            EffectProperties.Builder.of(LargeEntitiesEffect.class)
                    .id("large_entities")
                    .supplier(LargeEntitiesEffect::new)
                    .configure(LargeEntitiesEffect::configure)
                    .build();

    // ======================================================

    public LargeEntitiesEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableLargeEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableLargeEntities = false;
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 45);
    }
}
