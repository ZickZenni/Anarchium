package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BlindnessPotionEffect extends BasePotionEffectEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<BlindnessPotionEffect> PROPERTIES =
            EffectProperties.Builder.of(BlindnessPotionEffect.class)
                    .id("blindness")
                    .supplier(BlindnessPotionEffect::new)
                    .configure(BlindnessPotionEffect::configure)
                    .build();

    // ======================================================

    public BlindnessPotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.BLINDNESS, DURATION.get());
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 20);
    }
}
