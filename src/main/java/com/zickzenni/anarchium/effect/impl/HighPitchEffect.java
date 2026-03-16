package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;

public class HighPitchEffect extends TimedEffect
{
    public static final EffectProperties<HighPitchEffect> PROPERTIES =
            EffectProperties.Builder.of(HighPitchEffect.class)
                    .id("high_pitch")
                    .supplier(HighPitchEffect::new)
                    .conflict(LowPitchEffect.class)
                    .build();

    public HighPitchEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.customPitch = 2.0f;
        EffectStates.enableCustomPitch = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomPitch = false;
    }

    @Override
    public void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
        EffectStates.enableCustomPitch = true;

        super.onLevelTickClient(level, stage);
    }
}
