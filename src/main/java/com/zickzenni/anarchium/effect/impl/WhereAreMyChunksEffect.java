package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class WhereAreMyChunksEffect extends TimedEffect
{
    public static final EffectSupplier<WhereAreMyChunksEffect> SUPPLIER = WhereAreMyChunksEffect::new;

    public static final ResourceLocation ID = Anarchium.location("where_are_my_chunks");

    public static boolean ENABLED = false;

    public WhereAreMyChunksEffect()
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
