
package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ExplodeNearbyEntitiesEvent extends InstantEffect
{
    public static ModConfigSpec.ConfigValue<Integer> RADIUS;

    public static final EffectProperties<ExplodeNearbyEntitiesEvent> PROPERTIES =
            EffectProperties.Builder.of(ExplodeNearbyEntitiesEvent.class)
                    .id("explode_nearby_entities")
                    .supplier(ExplodeNearbyEntitiesEvent::new)
                    .configure(ExplodeNearbyEntitiesEvent::configure)
                    .build();

    // ======================================================

    public ExplodeNearbyEntitiesEvent()
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
                if (entity == null || entity.isRemoved() || entity instanceof ServerPlayer)
                {
                    continue;
                }

                var entityPosition = entity.position();
                level.explode(player, entityPosition.x, entityPosition.y, entityPosition.z, 2.2f, Level.ExplosionInteraction.MOB);
            }
        }
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        RADIUS = builder.define("radius", 50);
    }
}
