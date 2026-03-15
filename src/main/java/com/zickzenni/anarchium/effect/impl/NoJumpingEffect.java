package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class NoJumpingEffect extends TimedEffect
{
    public static final EffectSupplier<NoJumpingEffect> SUPPLIER = NoJumpingEffect::new;

    public static final ResourceLocation ID = Anarchium.location("no_jumping");

    public static boolean ENABLED = false;

    public NoJumpingEffect()
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
