package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DirtEffect extends InstantEffect
{
    public static final EffectProperties<DirtEffect> PROPERTIES =
            EffectProperties.Builder.of(DirtEffect.class)
                    .id("dirt")
                    .supplier(DirtEffect::new)
                    .build();

    public DirtEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
        {
            player.getInventory().add(new ItemStack(Items.DIRT, 1));
        }
    }
}
