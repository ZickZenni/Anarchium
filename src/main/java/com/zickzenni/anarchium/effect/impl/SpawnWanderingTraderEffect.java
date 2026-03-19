package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.storage.ServerLevelData;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class SpawnWanderingTraderEffect extends InstantEffect
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final EffectProperties<SpawnWanderingTraderEffect> PROPERTIES =
            EffectProperties.Builder.of(SpawnWanderingTraderEffect.class)
                    .id("spawn_wandering_trader")
                    .supplier(SpawnWanderingTraderEffect::new)
                    .build();

    public SpawnWanderingTraderEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
        {
            var blockPos = player.blockPosition();
            var level = player.serverLevel();

            try
            {
                Field serverLevelDataField = level.getClass().getDeclaredField("serverLevelData");
                serverLevelDataField.setAccessible(true);

                WanderingTrader trader = EntityType.WANDERING_TRADER.spawn(level, blockPos, MobSpawnType.EVENT);

                if (trader == null)
                {
                    continue;
                }

                for (int j = 0; j < 2; j++)
                {
                    TraderLlama traderllama = EntityType.TRADER_LLAMA.spawn(level, blockPos, MobSpawnType.EVENT);

                    if (traderllama != null)
                    {
                        traderllama.setLeashedTo(trader, true);
                    }
                }

                ((ServerLevelData) serverLevelDataField.get(level)).setWanderingTraderId(trader.getUUID());

                trader.setDespawnDelay(48000);
                trader.setWanderTarget(blockPos);
                trader.restrictTo(blockPos, 16);

                serverLevelDataField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e)
            {
                LOGGER.error("Failed to spawn wandering trader", e);
                return;
            }
        }
    }
}
