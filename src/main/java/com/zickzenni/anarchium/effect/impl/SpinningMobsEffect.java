package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SpinningMobsEffect extends TimedEffect
{
    public static final EffectProperties<SpinningMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(SpinningMobsEffect.class)
                    .id("spinning_mobs")
                    .supplier(SpinningMobsEffect::new)
                    .build();

    public SpinningMobsEffect()
    {
        super(PROPERTIES.getId(), 20 * 25);
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
            EffectStates.spinningLivingEntityRotation -= 16.4f * deltaTime;
        }
    }
}
