package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlindnessPotionEffect extends BasePotionEffectEffect
{
    public static final EffectProperties<BlindnessPotionEffect> PROPERTIES =
            EffectProperties.Builder.of(BlindnessPotionEffect.class)
                    .id("blindness")
                    .supplier(BlindnessPotionEffect::new)
                    .build();

    public static final int DURATION = 20;

    public BlindnessPotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.BLINDNESS, 20 * DURATION);
    }
}
