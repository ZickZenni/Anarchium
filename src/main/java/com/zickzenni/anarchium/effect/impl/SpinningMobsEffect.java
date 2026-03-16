package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SpinningMobsEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Double> SPEED;

    public static final EffectProperties<SpinningMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(SpinningMobsEffect.class)
                    .id("spinning_mobs")
                    .supplier(SpinningMobsEffect::new)
                    .configure(SpinningMobsEffect::configure)
                    .build();

    // ======================================================

    public SpinningMobsEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableSpinningLivingEntities = true;
        EffectStates.spinningLivingEntityRotation = 0;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableSpinningLivingEntities = false;
    }

    @Override
    public void onRenderLevel(RenderLevelStageEvent event, float deltaTime)
    {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY)
        {
            EffectStates.spinningLivingEntityRotation -= SPEED.get().floatValue() * deltaTime;
        }
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 35);
        SPEED = builder.defineInRange("speed", 16.4, 0.0, 250.0);
    }
}
