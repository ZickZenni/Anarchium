package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class RotatingCameraEffect extends TimedEffect
{
    public static final EffectProperties<RotatingCameraEffect> PROPERTIES =
            EffectProperties.Builder.of(RotatingCameraEffect.class)
                    .id("rotating_camera")
                    .supplier(RotatingCameraEffect::new)
                    .build();

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public RotatingCameraEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
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
            ROTATION += 9.67f * deltaTime;
        }
    }
}

