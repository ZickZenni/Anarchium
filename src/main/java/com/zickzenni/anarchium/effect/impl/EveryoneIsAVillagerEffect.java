package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

public class EveryoneIsAVillagerEffect extends TimedEffect
{
    public static final EffectSupplier<EveryoneIsAVillagerEffect> SUPPLIER = EveryoneIsAVillagerEffect::new;

    public static final ResourceLocation ID = Anarchium.location("everyone_is_a_villager");

    public static VillagerModel<Villager> VILLAGER_MODEL;

    public static boolean ENABLED = false;

    public EveryoneIsAVillagerEffect()
    {
        super(ID, 20 * 50);
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
