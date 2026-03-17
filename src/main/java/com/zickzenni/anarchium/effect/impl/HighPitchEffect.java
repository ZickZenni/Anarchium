package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;

public class HighPitchEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);

    public static final EffectProperties<HighPitchEffect> PROPERTIES =
            EffectProperties.Builder.of(HighPitchEffect.class)
                    .id("high_pitch")
                    .supplier(HighPitchEffect::new)
                    .conflict(LowPitchEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public HighPitchEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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
