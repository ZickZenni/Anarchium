package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class ReplaceEverySoundWithVillagersEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final EffectProperties<ReplaceEverySoundWithVillagersEffect> PROPERTIES =
            EffectProperties.Builder.of(ReplaceEverySoundWithVillagersEffect.class)
                    .id("replace_every_sound_with_villagers")
                    .supplier(ReplaceEverySoundWithVillagersEffect::new)
                    .config(DURATION)
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
}
