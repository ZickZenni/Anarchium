package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FatiguePotionEffect extends BasePotionEffectEffect
{
    public static final EffectProperties<FatiguePotionEffect> PROPERTIES =
            EffectProperties.Builder.of(FatiguePotionEffect.class)
                    .id("fatigue")
                    .supplier(FatiguePotionEffect::new)
                    .build();

    public static final int DURATION = 20;

    public FatiguePotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20 * DURATION);
    }
}
