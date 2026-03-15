package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class ForceSneakEffect extends TimedEffect
{
    public static final EffectSupplier<ForceSneakEffect> SUPPLIER = ForceSneakEffect::new;

    public static final ResourceLocation ID = Anarchium.location("force_sneak");

    public static boolean ENABLED = false;

    public ForceSneakEffect()
    {
        super(ID, 20 * 35);
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
