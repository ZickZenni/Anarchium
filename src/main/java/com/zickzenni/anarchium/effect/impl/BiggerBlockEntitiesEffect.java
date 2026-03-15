package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class BiggerBlockEntitiesEffect extends TimedEffect
{
    public static final EffectSupplier<BiggerBlockEntitiesEffect> SUPPLIER = BiggerBlockEntitiesEffect::new;

    public static final ResourceLocation ID = Anarchium.location("bigger_block_entities");

    public static final float SCALE_MULTIPLIER = 3.0f;

    public static boolean ENABLED = false;

    public BiggerBlockEntitiesEffect()
    {
        super(ID, 20 * 45);
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
