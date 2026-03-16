package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class RollingCameraEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final ConfigValue<Double> SPEED = ConfigValue.newDoubleInRange("speed", 7.67, 0.0, 250.0);

    public static final EffectProperties<RollingCameraEffect> PROPERTIES =
            EffectProperties.Builder.of(RollingCameraEffect.class)
                    .id("rolling_camera")
                    .supplier(RollingCameraEffect::new)
                    .config(DURATION)
                    .config(SPEED)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public RollingCameraEffect()
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

