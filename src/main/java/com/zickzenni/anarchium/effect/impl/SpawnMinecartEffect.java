package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
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
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (var player : server.getPlayerList().getPlayers())
        {
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.MINECART)
            {
                continue;
            }

            var minecart = EntityType.MINECART.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.NATURAL);

            if (minecart == null)
            {
                LOGGER.error("Failed to spawn minecart for player {}", player.getDisplayName());
                continue;
            }

            player.startRiding(minecart);
        }
    }
}
