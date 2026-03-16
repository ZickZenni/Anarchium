package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EntityMagnetEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Integer> RADIUS;

    public static final EffectProperties<EntityMagnetEffect> PROPERTIES =
            EffectProperties.Builder.of(EntityMagnetEffect.class)
                    .id("entity_magnet")
                    .supplier(EntityMagnetEffect::new)
                    .configure(EntityMagnetEffect::configure)
                    .build();

    // ======================================================

    public EntityMagnetEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickServer(ServerLevel ignoredLevel, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            for (var player : AnarchiumServer.getPlayers())
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 30);
        RADIUS = builder.define("radius", 10);
    }
}
