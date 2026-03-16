package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SkeletonsHaveSpinbotEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Double> SPEED;

    public static final EffectProperties<SkeletonsHaveSpinbotEffect> PROPERTIES =
            EffectProperties.Builder.of(SkeletonsHaveSpinbotEffect.class)
                    .id("skeletons_have_spinbot")
                    .supplier(SkeletonsHaveSpinbotEffect::new)
                    .configure(SkeletonsHaveSpinbotEffect::configure)
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 40);
        SPEED = builder.defineInRange("speed", 72.4, 0.0, 400.0);
    }
}
