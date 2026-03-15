package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class JailEffect extends InstantEffect
{
    public static final EffectSupplier<JailEffect> SUPPLIER = JailEffect::new;

    public static final ResourceLocation ID = Anarchium.location("jail");

    public JailEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            var position = player.blockPosition();
            var level = player.serverLevel();

            for (int y = position.getY() - 1; y < position.getY() + 3; y++)
            {
                for (int x = position.getX() - 1; x < position.getX() + 2; x++)
                {
                    for (int z = position.getZ() - 1; z < position.getZ() + 2; z++)
                    {
                        var blockPosition = new BlockPos(x, y, z);
                        BlockState blockState;

                        if (y == position.getY() - 1)
                        {
                            blockState = Blocks.STONE_BRICKS.defaultBlockState();
                        } else
                        {
                            if (x == position.getX() && z == position.getZ())
                            {
                                blockState = Blocks.AIR.defaultBlockState();
                            } else
                            {
                                blockState = Blocks.IRON_BARS.defaultBlockState();
                            }
                        }

                        level.setBlock(blockPosition, blockState, 3);
                    }
                }
            }
        }
    }
}
