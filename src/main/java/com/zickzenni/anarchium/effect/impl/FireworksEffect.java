package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.FireworkUtil;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

public class FireworksEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);
    public static final ConfigValue<Integer> RADIUS = ConfigValue.newInteger("radius", 12);
    private static final RandomSource RANDOM = RandomSource.create();    public static final EffectProperties<FireworksEffect> PROPERTIES =
            EffectProperties.Builder.of(FireworksEffect.class)
                    .id("fireworks")
                    .supplier(FireworksEffect::new)
                    .config(DURATION)
                    .config(RADIUS)
                    .build();
    private int ticks;
    public FireworksEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickServer(ServerLevel ignoredLevel, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            if (ticks <= 0)
            {
                var radius = RADIUS.get();

                for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
                {
                    var level = player.serverLevel();
                    var amount = RANDOM.nextIntBetweenInclusive(1, 3);

                    for (var i = 0; i < amount; i++)
                    {
                        var position = player.blockPosition();
                        var posX = position.getX() + (RANDOM.nextInt(radius) - RANDOM.nextInt(radius));
                        var posZ = position.getZ() + (RANDOM.nextInt(radius) - RANDOM.nextInt(radius));

                        for (var posY = position.getY() - radius; posY < position.getY() + radius; posY++)
                        {
                            var blockPos = new BlockPos(posX, posY, posZ);
                            var blockState = level.getBlockState(blockPos);
                            var collision = blockState.getCollisionShape(level, blockPos);

                            if (collision.isEmpty())
                            {
                                FireworkUtil.summonFirework(
                                        FireworkUtil.createRandomFirework(),
                                        level,
                                        posX + 0.5D,
                                        posY,
                                        posZ + 0.5D
                                );
                                break;
                            }
                        }
                    }
                }

                ticks = RANDOM.nextIntBetweenInclusive(9, 17);
            } else
            {
                ticks--;
            }
        }

        super.onLevelTickServer(ignoredLevel, stage);
    }



}
