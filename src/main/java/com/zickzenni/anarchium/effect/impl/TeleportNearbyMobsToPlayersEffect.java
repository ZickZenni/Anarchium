package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TeleportNearbyMobsToPlayersEffect extends InstantEffect
{
    public static ModConfigSpec.ConfigValue<Integer> RADIUS;

    public static final EffectProperties<TeleportNearbyMobsToPlayersEffect> PROPERTIES =
            EffectProperties.Builder.of(TeleportNearbyMobsToPlayersEffect.class)
                    .id("teleport_nearby_mobs_to_players")
                    .supplier(TeleportNearbyMobsToPlayersEffect::new)
                    .configure(TeleportNearbyMobsToPlayersEffect::configure)
                    .build();

    // ======================================================

    public TeleportNearbyMobsToPlayersEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        int radius = RADIUS.get();

        for (var player : AnarchiumServer.getPlayers())
        {
            var level = player.serverLevel();
            var playerPosition = player.position();
            var entities = level.getEntities(player, new AABB(playerPosition.add(radius, radius, radius), playerPosition.add(-radius, -radius, -radius)));

            for (var entity : entities)
            {
                if (entity == null || entity.isRemoved() || entity instanceof ServerPlayer || !(entity instanceof LivingEntity))
                {
                    continue;
                }

                entity.teleportTo(playerPosition.x, playerPosition.y, playerPosition.z);
            }
        }
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        RADIUS = builder.define("radius", 50);
    }
}
