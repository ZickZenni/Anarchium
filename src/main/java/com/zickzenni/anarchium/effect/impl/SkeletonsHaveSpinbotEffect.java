package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SkeletonsHaveSpinbotEffect extends TimedEffect
{
    public static final EffectProperties<SkeletonsHaveSpinbotEffect> PROPERTIES =
            EffectProperties.Builder.of(SkeletonsHaveSpinbotEffect.class)
                    .id("skeletons_have_spinbot")
                    .supplier(SkeletonsHaveSpinbotEffect::new)
                    .build();

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public SkeletonsHaveSpinbotEffect()
    {
        super(PROPERTIES.getId(), 20 * 40);
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
    }

    @Override
    public void onRenderLevel(RenderLevelStageEvent event, float deltaTime)
    {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY)
        {
            ROTATION -= 72.4f * deltaTime;
        }
    }
}
