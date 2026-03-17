package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.Logger;

public class SpawnBoatEffect extends InstantEffect
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final EffectProperties<SpawnBoatEffect> PROPERTIES =
            EffectProperties.Builder.of(SpawnBoatEffect.class)
                    .id("spawn_boat")
                    .supplier(SpawnBoatEffect::new)
                    .build();

    public SpawnBoatEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.BOAT)
            {
                continue;
            }

            var boat = EntityType.BOAT.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.NATURAL);

            if (boat == null)
            {
                LOGGER.error("Failed to spawn boat for player {}", player.getDisplayName());
                continue;
            }

            player.startRiding(boat);
        }
    }
}
