package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class RollingCameraEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Double> SPEED;

    public static final EffectProperties<RollingCameraEffect> PROPERTIES =
            EffectProperties.Builder.of(RollingCameraEffect.class)
                    .id("rolling_camera")
                    .supplier(RollingCameraEffect::new)
                    .configure(RollingCameraEffect::configure)
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 30);
        SPEED = builder.defineInRange("speed", 7.67, 0.0, 250.0);
    }
}

