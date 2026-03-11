package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;

public class InvertedFieldOfViewEffect extends TimedEffect
{
    public static final EffectSupplier<InvertedFieldOfViewEffect> SUPPLIER = InvertedFieldOfViewEffect::new;

    public static final Identifier ID = Anarchium.identifier("inverted_field_of_view");

    public InvertedFieldOfViewEffect()
    {
        super(ID, 300);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }

    @Override
    public void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
        }

        super.onLevelTickClient(level, stage);
    }
}

