package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class TeleportNearbyMobsToPlayersEffect extends InstantEffect
{
    public static final EffectSupplier<TeleportNearbyMobsToPlayersEffect> SUPPLIER = TeleportNearbyMobsToPlayersEffect::new;

    public static final ResourceLocation ID = Anarchium.location("teleport_nearby_mobs_to_players");

    public static final float RADIUS = 50;

    public TeleportNearbyMobsToPlayersEffect()
    {
        super(ID);
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

                entity.teleportTo(playerPosition.x, playerPosition.y, playerPosition.z);
            }
        }
    }
}
