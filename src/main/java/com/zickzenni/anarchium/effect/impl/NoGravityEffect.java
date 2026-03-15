package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class NoGravityEffect extends TimedEffect
{
    public static final EffectSupplier<NoGravityEffect> SUPPLIER = NoGravityEffect::new;

    public static final ResourceLocation ID = Anarchium.location("no_gravity");

    public static boolean ENABLED = false;

    public NoGravityEffect()
    {
        super(ID, 20 * 40);
    }

    @Override
    public void onStartServer()
    {
        ENABLED = true;
    }

    @Override
    public void onEndServer()
    {
        ENABLED = false;
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
