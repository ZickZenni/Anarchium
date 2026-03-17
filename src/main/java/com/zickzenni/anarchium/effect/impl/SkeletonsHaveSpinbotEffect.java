package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SkeletonsHaveSpinbotEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 40);

    public static final ConfigValue<Double> SPEED = ConfigValue.newDoubleInRange("speed", 72.4, 0.0, 400.0);

    public static final EffectProperties<SkeletonsHaveSpinbotEffect> PROPERTIES =
            EffectProperties.Builder.of(SkeletonsHaveSpinbotEffect.class)
                    .id("skeletons_have_spinbot")
                    .supplier(SkeletonsHaveSpinbotEffect::new)
                    .config(DURATION)
                    .config(SPEED)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public SkeletonsHaveSpinbotEffect()
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
            ROTATION -= SPEED.get().floatValue() * deltaTime;
        }
    }
}
