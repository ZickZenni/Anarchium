package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

public class EntityMagnetEffect extends TimedEffect
{
    public static final EffectProperties<EntityMagnetEffect> PROPERTIES =
            EffectProperties.Builder.of(EntityMagnetEffect.class)
                    .id("entity_magnet")
                    .supplier(EntityMagnetEffect::new)
                    .build();

    public static final float RADIUS = 8;

    public EntityMagnetEffect()
    {
        super(PROPERTIES.getId(), 20 * 30);
    }

    @Override
    public void onLevelTickServer(ServerLevel ignoredLevel, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            for (var player : AnarchiumServer.getPlayers())
            {
                var playerPosition = player.position();
                var entities = player.serverLevel().getEntities(player, new AABB(playerPosition.add(RADIUS, RADIUS, RADIUS), playerPosition.add(-RADIUS, -RADIUS, -RADIUS)));

                for (var entity : entities)
                {
                    if (entity == null || entity.isRemoved() || entity instanceof ServerPlayer)
                    {
                        continue;
                    }

                    var direction = playerPosition.subtract(entity.position());
                    var distance = direction.length();

                    float speed = 0.7f;

                    /*
                     * Slow down when the entity is already near the player.
                     * Without it there is no outplay potential.
                     */
                    if (distance <= 4.5f) {
                        speed *= (float) (distance / 4.5f);
                    }

                    var deltaMovement = direction.normalize().multiply(speed, speed, speed);

                    entity.resetFallDistance();
                    entity.setDeltaMovement(deltaMovement.x, deltaMovement.y, deltaMovement.z);
                }
            }
        }

        super.onLevelTickServer(ignoredLevel, stage);
    }
}
