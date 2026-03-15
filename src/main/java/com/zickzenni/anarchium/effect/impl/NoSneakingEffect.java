package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class NoSneakingEffect extends TimedEffect
{
    public static final EffectSupplier<NoSneakingEffect> SUPPLIER = NoSneakingEffect::new;

    public static final ResourceLocation ID = Anarchium.location("no_sneaking");

    public static boolean ENABLED = false;

    public NoSneakingEffect()
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
