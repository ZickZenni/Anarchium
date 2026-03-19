package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GiveDiamondsEffect extends InstantEffect
{
    public static final EffectProperties<GiveDiamondsEffect> PROPERTIES =
            EffectProperties.Builder.of(GiveDiamondsEffect.class)
                    .id("give_diamonds")
                    .supplier(GiveDiamondsEffect::new)
                    .build();

    public GiveDiamondsEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
        {
            player.getInventory().add(new ItemStack(Items.DIAMOND, player.level().random.nextInt(10) + 1));
        }
    }
}
