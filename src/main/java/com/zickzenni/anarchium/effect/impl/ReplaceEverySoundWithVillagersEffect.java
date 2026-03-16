package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ReplaceEverySoundWithVillagersEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<ReplaceEverySoundWithVillagersEffect> PROPERTIES =
            EffectProperties.Builder.of(ReplaceEverySoundWithVillagersEffect.class)
                    .id("replace_every_sound_with_villagers")
                    .supplier(ReplaceEverySoundWithVillagersEffect::new)
                    .configure(ReplaceEverySoundWithVillagersEffect::configure)
                    .build();

    // ======================================================

    public ReplaceEverySoundWithVillagersEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableReplaceEverySoundWithVillagers = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableReplaceEverySoundWithVillagers = false;
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 30);
    }
}
