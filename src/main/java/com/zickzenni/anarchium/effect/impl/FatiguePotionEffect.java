package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.BasePotionEffectEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FatiguePotionEffect extends BasePotionEffectEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 20);
    public static final EffectProperties<FatiguePotionEffect> PROPERTIES =
            EffectProperties.Builder.of(FatiguePotionEffect.class)
                    .id("fatigue")
                    .supplier(FatiguePotionEffect::new)
                    .config(DURATION)
                    .build();

    public FatiguePotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.DIG_SLOWDOWN, DURATION.get());
    }
}
