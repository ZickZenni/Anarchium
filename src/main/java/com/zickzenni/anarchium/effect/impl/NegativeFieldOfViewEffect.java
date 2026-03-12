package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.effect.event.EffectRenderLevelStageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class NegativeFieldOfViewEffect extends TimedEffect
{
    public static final EffectSupplier<NegativeFieldOfViewEffect> SUPPLIER = NegativeFieldOfViewEffect::new;

    public static final Identifier ID = Anarchium.identifier("negative_field_of_view");

    public NegativeFieldOfViewEffect()
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
    public void onRenderLevel(EffectRenderLevelStageEvent event, float deltaTime)
    {
        if (event.stage() == EffectRenderLevelStageEvent.Stage.AFTER_SKY)
        {
            EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
        }
    }
}

