package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class RollingCameraEffect extends TimedEffect
{
    public static final EffectSupplier<RollingCameraEffect> SUPPLIER = RollingCameraEffect::new;

    public static final ResourceLocation ID = Anarchium.location("rolling_camera");

    public static boolean ENABLED = false;

    public static float ROTATION = 0;

    public RollingCameraEffect()
    {
        super(ID, 20 * 30);
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
            ROTATION += 7.67f * deltaTime;
        }
    }
}

