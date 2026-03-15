package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class QuakeFieldOfViewEffect extends TimedEffect
{
    public static final EffectSupplier<QuakeFieldOfViewEffect> SUPPLIER = QuakeFieldOfViewEffect::new;

    public static final ResourceLocation ID = Anarchium.location("quake_field_of_view");

    public static final int FOV = 130;

    public QuakeFieldOfViewEffect()
    {
        super(ID, 20 * 30);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = FOV;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }
}

