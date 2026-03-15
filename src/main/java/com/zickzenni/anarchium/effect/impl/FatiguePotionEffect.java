package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FatiguePotionEffect extends BasePotionEffectEffect
{
    public static final EffectSupplier<FatiguePotionEffect> SUPPLIER = FatiguePotionEffect::new;

    public static final ResourceLocation ID = Anarchium.location("fatigue");

    public static final int DURATION = 20;

    public FatiguePotionEffect()
    {
        super(ID);
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20 * DURATION);
    }
}
