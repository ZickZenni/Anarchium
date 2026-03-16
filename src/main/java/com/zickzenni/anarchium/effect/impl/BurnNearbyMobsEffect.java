package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class BurnNearbyMobsEffect extends InstantEffect
{
    public static final EffectProperties<BurnNearbyMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(BurnNearbyMobsEffect.class)
                    .id("burn_nearby_mobs")
                    .supplier(BurnNearbyMobsEffect::new)
                    .build();

    public static final float RADIUS = 50;

    public BurnNearbyMobsEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            var level = player.serverLevel();
            var playerPosition = player.position();
            var entities = level.getEntities(player, new AABB(playerPosition.add(RADIUS, RADIUS, RADIUS), playerPosition.add(-RADIUS, -RADIUS, -RADIUS)));

            for (var entity : entities)
            {
                if (entity == null || entity.isRemoved() || entity instanceof ServerPlayer || !(entity instanceof LivingEntity))
                {
                    continue;
                }

                entity.igniteForSeconds(8.0f);
            }
        }
    }
}
