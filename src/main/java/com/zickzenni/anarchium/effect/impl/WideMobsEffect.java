package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class WideMobsEffect extends TimedEffect
{
    public static final EffectSupplier<WideMobsEffect> SUPPLIER = WideMobsEffect::new;

    public static final ResourceLocation ID = Anarchium.location("wide_mobs");

    public WideMobsEffect()
    {
        super(ID, 20 * 45);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableWideLivingEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableWideLivingEntities = false;
    }
}
