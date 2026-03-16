package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;

public class ReplaceEverySoundWithVillagersEffect extends TimedEffect
{
    public static final EffectProperties<ReplaceEverySoundWithVillagersEffect> PROPERTIES =
            EffectProperties.Builder.of(ReplaceEverySoundWithVillagersEffect.class)
                    .id("replace_every_sound_with_villagers")
                    .supplier(ReplaceEverySoundWithVillagersEffect::new)
                    .build();

    public ReplaceEverySoundWithVillagersEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
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
