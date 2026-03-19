package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.level.block.Blocks;

public class PlaceLavaEffect extends InstantEffect
{
    public static final EffectProperties<PlaceLavaEffect> PROPERTIES =
            EffectProperties.Builder.of(PlaceLavaEffect.class)
                    .id("place_lava")
                    .supplier(PlaceLavaEffect::new)
                    .build();

    public PlaceLavaEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
        {
            var level = player.serverLevel();
            level.setBlock(player.blockPosition(), Blocks.LAVA.defaultBlockState(), 3);
        }
    }
}
