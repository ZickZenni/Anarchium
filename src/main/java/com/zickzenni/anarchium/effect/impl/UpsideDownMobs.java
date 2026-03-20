package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.world.entity.LivingEntity;

public class UpsideDownMobs extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static boolean ENABLED = false;    public static final EffectProperties<UpsideDownMobs> PROPERTIES =
            EffectProperties.Builder.of(UpsideDownMobs.class)
                    .id("upside_down_mobs")
                    .supplier(UpsideDownMobs::new)
                    .config(DURATION)
                    .build();

    public UpsideDownMobs()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static <T extends LivingEntity> void modifyRotation$LivingEntityRenderer(T entity, PoseStack stack)
    {
        stack.translate(0, entity.getBbHeight(), 0);
        stack.mulPose(Axis.XP.rotationDegrees(180.0f));
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
