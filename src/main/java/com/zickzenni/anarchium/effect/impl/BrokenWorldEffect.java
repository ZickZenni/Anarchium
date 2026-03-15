package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class BrokenWorldEffect extends TimedEffect
{
    public static final EffectSupplier<BrokenWorldEffect> SUPPLIER = BrokenWorldEffect::new;

    public static final ResourceLocation ID = Anarchium.location("broken_world");

    public static boolean ENABLED = false;

    public BrokenWorldEffect()
    {
        super(ID, 20 * 25);
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
