package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BurnNearbyMobsEffect extends InstantEffect
{
    public static ModConfigSpec.ConfigValue<Integer> RADIUS;

    public static final EffectProperties<BurnNearbyMobsEffect> PROPERTIES =
            EffectProperties.Builder.of(BurnNearbyMobsEffect.class)
                    .id("burn_nearby_mobs")
                    .supplier(BurnNearbyMobsEffect::new)
                    .configure(BurnNearbyMobsEffect::configure)
                    .build();

    // ======================================================

    public BurnNearbyMobsEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            int radius = RADIUS.get();
            var level = player.serverLevel();
            var playerPosition = player.position();
            var entities =
                    level.getEntities(player, new AABB(playerPosition.add(radius, radius, radius), playerPosition.add(-radius, -radius, -radius)));

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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        RADIUS = builder.define("radius", 50);
    }
}
