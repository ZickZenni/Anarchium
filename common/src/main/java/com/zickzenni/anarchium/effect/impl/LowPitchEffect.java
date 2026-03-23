package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import net.minecraft.client.multiplayer.ClientLevel;

public class LowPitchEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<LowPitchEffect> PROPERTIES = EffectProperties.Builder.of(LowPitchEffect.class)
            .id("low_pitch")
            .supplier(LowPitchEffect::new)
            .conflict(HighPitchEffect.class)
            .config(DURATION)
            .build();

    public LowPitchEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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
    public void onLevelTickClient(ILevelTickEvent<ClientLevel> event)
    {
        EffectStates.enableCustomPitch = true;

        super.onLevelTickClient(event);
    }
}
