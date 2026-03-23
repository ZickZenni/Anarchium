package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.AABB;

public class PlaceNearbyMobsIntoBoatEffect extends InstantEffect
{
    public static final ConfigValue<Integer> RADIUS = ConfigValue.newInteger("radius", 50);
    public static final EffectProperties<PlaceNearbyMobsIntoBoatEffect> PROPERTIES =
            EffectProperties.Builder.of(PlaceNearbyMobsIntoBoatEffect.class)
                    .id("place_nearby_mobs_into_boat")
                    .supplier(PlaceNearbyMobsIntoBoatEffect::new)
                    .config(RADIUS)
                    .build();

    public PlaceNearbyMobsIntoBoatEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            int radius = RADIUS.get();
            var level = player.serverLevel();
            var playerPosition = player.position();
            var entities = level.getEntities(
                    player,
                    new AABB(playerPosition.add(radius, radius, radius), playerPosition.add(-radius, -radius, -radius))
            );

            for (var entity : entities)
            {
                if (entity == null || entity.isRemoved() || entity.getVehicle() != null || entity instanceof ServerPlayer || !(entity instanceof LivingEntity))
                {
                    continue;
                }

                var boat = EntityType.BOAT.spawn(level, entity.blockPosition(), MobSpawnType.NATURAL);

                if (boat == null)
                {
                    continue;
                }

                entity.startRiding(boat);
            }
        }
    }
}
