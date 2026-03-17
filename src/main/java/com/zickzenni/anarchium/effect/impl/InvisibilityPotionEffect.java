package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class InvisibilityPotionEffect extends BasePotionEffectEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 40);

    public static final EffectProperties<InvisibilityPotionEffect> PROPERTIES =
            EffectProperties.Builder.of(InvisibilityPotionEffect.class)
                    .id("invisibility")
                    .supplier(InvisibilityPotionEffect::new)
                    .config(DURATION)
                    .build();

    // ======================================================

    public InvisibilityPotionEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected MobEffectInstance getPotionEffect(ServerPlayer player)
    {
        return new MobEffectInstance(MobEffects.INVISIBILITY, DURATION.get());
    }
}
