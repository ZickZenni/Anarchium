package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;

public class LowPitchEffect extends TimedEffect
{
    public static final EffectProperties<LowPitchEffect> PROPERTIES =
            EffectProperties.Builder.of(LowPitchEffect.class)
                    .id("low_pitch")
                    .supplier(LowPitchEffect::new)
                    .conflict(HighPitchEffect.class)
                    .build();

    public LowPitchEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.customPitch = 0.5f;
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
