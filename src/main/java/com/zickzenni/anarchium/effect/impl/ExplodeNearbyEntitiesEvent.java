
package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ExplodeNearbyEntitiesEvent extends InstantEffect
{
    public static final ConfigValue<Integer> RADIUS = ConfigValue.newInteger("radius", 50);
    public static final EffectProperties<ExplodeNearbyEntitiesEvent> PROPERTIES =
            EffectProperties.Builder.of(ExplodeNearbyEntitiesEvent.class)
                    .id("explode_nearby_entities")
                    .supplier(ExplodeNearbyEntitiesEvent::new)
                    .config(RADIUS)
                    .build();

    public ExplodeNearbyEntitiesEvent()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
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
}
