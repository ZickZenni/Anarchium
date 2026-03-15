package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;

public class HighPitchEffect extends TimedEffect
{
    public static final EffectSupplier<HighPitchEffect> SUPPLIER = HighPitchEffect::new;

    public static final ResourceLocation ID = Anarchium.location("high_pitch");

    public HighPitchEffect()
    {
        super(ID, 20 * 30);
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
