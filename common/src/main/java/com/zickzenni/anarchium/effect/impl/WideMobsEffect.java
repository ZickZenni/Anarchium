package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.world.entity.LivingEntity;

public class WideMobsEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<WideMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(WideMobsEffect.class)
                    .id("wide_mobs")
                    .supplier(WideMobsEffect::new)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public WideMobsEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static <T extends LivingEntity> void modifyWidth$LivingEntityRenderer(T entity, PoseStack stack)
    {
        var rotation = 180.0F - entity.yBodyRot;
        stack.mulPose(Axis.YP.rotationDegrees(rotation));
        stack.scale(2, 1, 1);
        stack.mulPose(Axis.YP.rotationDegrees(-rotation));
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
}
