package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class NegativeFieldOfViewEffect extends TimedEffect
{
    public static final EffectSupplier<NegativeFieldOfViewEffect> SUPPLIER = NegativeFieldOfViewEffect::new;

    public static final ResourceLocation ID = Anarchium.location("negative_field_of_view");

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
    public void onRenderLevel(RenderLevelStageEvent event, float deltaTime)
    {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY)
        {
            EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
        }
    }
}

