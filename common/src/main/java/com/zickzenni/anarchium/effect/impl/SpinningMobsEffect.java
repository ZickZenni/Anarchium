package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderLevelEvent;

public class SpinningMobsEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);
    public static final ConfigValue<Double> SPEED = ConfigValue.newDoubleInRange("speed", 25, 0.0, 250.0);
    public static final EffectProperties<SpinningMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(SpinningMobsEffect.class)
                    .id("spinning_mobs")
                    .supplier(SpinningMobsEffect::new)
                    .config(DURATION)
                    .config(SPEED)
                    .build();

    public static boolean ENABLED = false;
    public static float ROTATION = 0;

    public SpinningMobsEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static void modifyRotation$LivingEntityRenderer(PoseStack stack)
    {
        stack.mulPose(Axis.YP.rotationDegrees(ROTATION));
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
        ROTATION = 0;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
        ROTATION = 0;
    }

    @Override
    public void onRenderLevel(IRenderLevelEvent event)
    {
        if (event.getStage() == IRenderLevelEvent.Stage.START)
        {
            ROTATION -= SPEED.get().floatValue() * event.getPartialTick().getRealtimeDeltaTicks();
        }
    }
}
