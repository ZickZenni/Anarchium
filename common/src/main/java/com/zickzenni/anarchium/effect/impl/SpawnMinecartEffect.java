package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.Logger;

public class SpawnMinecartEffect extends InstantEffect
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final EffectProperties<SpawnMinecartEffect> PROPERTIES =
            EffectProperties.Builder.of(SpawnMinecartEffect.class)
                    .id("spawn_minecart")
                    .supplier(SpawnMinecartEffect::new)
                    .build();

    public SpawnMinecartEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.MINECART)
            {
                continue;
            }

            var minecart =
                    EntityType.MINECART.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.NATURAL);

            if (minecart == null)
            {
                LOGGER.error("Failed to spawn minecart for player {}", player.getDisplayName());
                continue;
            }

            player.startRiding(minecart);
        }
    }
}
