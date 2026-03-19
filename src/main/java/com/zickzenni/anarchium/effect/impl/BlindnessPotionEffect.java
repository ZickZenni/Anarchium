package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.BasePotionEffectEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlindnessPotionEffect extends BasePotionEffectEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 20);
    public static final EffectProperties<BlindnessPotionEffect> PROPERTIES =
            EffectProperties.Builder.of(BlindnessPotionEffect.class)
                    .id("blindness")
                    .supplier(BlindnessPotionEffect::new)
                    .config(DURATION)
                    .build();

    public BlindnessPotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.BLINDNESS, DURATION.get());
    }
}
