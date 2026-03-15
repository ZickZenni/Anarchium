package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class WhereIsTheSkyEffect extends TimedEffect
{
    public static final EffectSupplier<WhereIsTheSkyEffect> SUPPLIER = WhereIsTheSkyEffect::new;

    public static final ResourceLocation ID = Anarchium.location("where_is_the_sky");

    public static boolean ENABLED = false;

    public WhereIsTheSkyEffect()
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
