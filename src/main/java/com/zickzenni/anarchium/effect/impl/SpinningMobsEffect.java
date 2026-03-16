package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SpinningMobsEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);

    public static final ConfigValue<Double> SPEED = ConfigValue.newDoubleInRange("speed", 16.4, 0.0, 250.0);

    public static final EffectProperties<SpinningMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(SpinningMobsEffect.class)
                    .id("spinning_mobs")
                    .supplier(SpinningMobsEffect::new)
                    .config(DURATION)
                    .config(SPEED)
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
}
