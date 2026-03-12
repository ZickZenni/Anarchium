package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class LargeEntitiesEffect extends TimedEffect
{
    public static final EffectSupplier<LargeEntitiesEffect> SUPPLIER = LargeEntitiesEffect::new;

    public static final ResourceLocation ID = Anarchium.location("large_entities");

    public LargeEntitiesEffect()
    {
        super(ID, 20 * 45);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableLargeEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableLargeEntities = false;
    }
}
