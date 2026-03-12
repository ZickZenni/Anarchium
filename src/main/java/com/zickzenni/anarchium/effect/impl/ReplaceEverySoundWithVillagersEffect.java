package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class ReplaceEverySoundWithVillagersEffect extends TimedEffect
{
    public static final EffectSupplier<ReplaceEverySoundWithVillagersEffect> SUPPLIER = ReplaceEverySoundWithVillagersEffect::new;

    public static final ResourceLocation ID = Anarchium.location("replace_every_sound_with_villagers");

    public ReplaceEverySoundWithVillagersEffect()
    {
        super(ID, 20 * 30);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableReplaceEverySoundWithVillagers = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableReplaceEverySoundWithVillagers = false;
    }
}
