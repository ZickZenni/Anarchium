package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class PlaceLavaEffect extends InstantEffect
{
    public static final EffectSupplier<PlaceLavaEffect> SUPPLIER = PlaceLavaEffect::new;

    public static final ResourceLocation ID = Anarchium.location("place_lava");

    public PlaceLavaEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            var level = player.serverLevel();
            level.setBlock(player.blockPosition(), Blocks.LAVA.defaultBlockState(), 3);
        }
    }
}
