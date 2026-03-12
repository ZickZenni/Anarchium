package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class SkeletonsHaveSpinbotEffect extends TimedEffect
{
    public static final EffectSupplier<SkeletonsHaveSpinbotEffect> SUPPLIER = SkeletonsHaveSpinbotEffect::new;

    public static final ResourceLocation ID = Anarchium.location("skeletons_have_spinbot");

    public static boolean enabled = false;

    public static float rotation = 0;

    public SkeletonsHaveSpinbotEffect()
    {
        super(ID, 20 * 40);
    }

    @Override
    public void onStartClient()
    {
        enabled = true;
    }

    @Override
    public void onEndClient()
    {
        enabled = false;
    }

    @Override
    public void onRenderLevel(RenderLevelStageEvent event, float deltaTime)
    {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY)
        {
            rotation -= 72.4f * deltaTime;
        }
    }
}
