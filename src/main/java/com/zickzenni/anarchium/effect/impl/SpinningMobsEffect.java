package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SpinningMobsEffect extends TimedEffect
{
    public static final EffectSupplier<SpinningMobsEffect> SUPPLIER = SpinningMobsEffect::new;

    public static final ResourceLocation ID = Anarchium.location("spinning_mobs");

    public SpinningMobsEffect()
    {
        super(ID, 20 * 25);
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
