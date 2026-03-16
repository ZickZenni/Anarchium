
package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ExplodeNearbyEntitiesEvent extends InstantEffect
{
    public static final EffectProperties<ExplodeNearbyEntitiesEvent> PROPERTIES =
            EffectProperties.Builder.of(ExplodeNearbyEntitiesEvent.class)
                    .id("explode_nearby_entities")
                    .supplier(ExplodeNearbyEntitiesEvent::new)
                    .build();

    public ExplodeNearbyEntitiesEvent()
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
            var entities = level.getEntities(player, new AABB(playerPosition.add(70, 70, 70), playerPosition.add(-70, -70, -70)));

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
}
