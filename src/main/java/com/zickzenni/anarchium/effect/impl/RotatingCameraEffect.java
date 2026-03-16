package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class RotatingCameraEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final ConfigValue<Double> SPEED = ConfigValue.newDoubleInRange("speed", 9.67, 0.0, 250.0);

    public static final EffectProperties<RotatingCameraEffect> PROPERTIES =
            EffectProperties.Builder.of(RotatingCameraEffect.class)
                    .id("rotating_camera")
                    .supplier(RotatingCameraEffect::new)
                    .config(DURATION)
                    .config(SPEED)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public RotatingCameraEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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
            ROTATION += SPEED.get().floatValue() * deltaTime;
        }
    }
}

