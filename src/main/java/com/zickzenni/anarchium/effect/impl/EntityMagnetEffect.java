package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

public class EntityMagnetEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);
    public static final ConfigValue<Integer> RADIUS = ConfigValue.newInteger("radius", 10);
    public static final EffectProperties<EntityMagnetEffect> PROPERTIES =
            EffectProperties.Builder.of(EntityMagnetEffect.class)
                    .id("entity_magnet")
                    .supplier(EntityMagnetEffect::new)
                    .config(DURATION)
                    .config(RADIUS)
                    .build();

    public EntityMagnetEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickServer(ServerLevel ignoredLevel, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            for (var player : Anarchium.getServer().getOnlinePlayers())
            {
                int radius = RADIUS.get();
                var playerPosition = player.position();
                var entities = player.serverLevel()
                        .getEntities(player, new AABB(playerPosition.add(radius, radius, radius), playerPosition.add(-radius, -radius, -radius)));

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
                    if (distance <= 4.5f)
                    {
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
