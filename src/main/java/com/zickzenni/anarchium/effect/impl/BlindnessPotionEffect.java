package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlindnessPotionEffect extends BasePotionEffectEffect
{
    public static final EffectSupplier<BlindnessPotionEffect> SUPPLIER = BlindnessPotionEffect::new;

    public static final ResourceLocation ID = Anarchium.location("blindness");

    public static final int DURATION = 20;

    public BlindnessPotionEffect()
    {
        super(ID);
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.BLINDNESS, 20 * DURATION);
    }
}
