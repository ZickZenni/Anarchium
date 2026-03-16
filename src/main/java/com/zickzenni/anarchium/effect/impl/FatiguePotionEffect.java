package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FatiguePotionEffect extends BasePotionEffectEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<FatiguePotionEffect> PROPERTIES =
            EffectProperties.Builder.of(FatiguePotionEffect.class)
                    .id("fatigue")
                    .supplier(FatiguePotionEffect::new)
                    .configure(FatiguePotionEffect::configure)
                    .build();

    // ======================================================

    public FatiguePotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.DIG_SLOWDOWN, DURATION.get());
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 20);
    }
}
