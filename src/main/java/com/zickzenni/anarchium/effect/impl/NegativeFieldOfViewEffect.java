package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class NegativeFieldOfViewEffect extends TimedEffect
{
    public static final EffectProperties<NegativeFieldOfViewEffect> PROPERTIES =
            EffectProperties.Builder.of(NegativeFieldOfViewEffect.class)
                    .id("negative_field_of_view")
                    .supplier(NegativeFieldOfViewEffect::new)
                    .conflict(QuakeFieldOfViewEffect.class)
                    .build();

    public NegativeFieldOfViewEffect()
    {
        super(PROPERTIES.getId(), 300);
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

