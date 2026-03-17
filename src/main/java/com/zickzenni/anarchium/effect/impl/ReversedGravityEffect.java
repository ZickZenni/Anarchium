package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;

public class ReversedGravityEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final EffectProperties<ReversedGravityEffect> PROPERTIES =
            EffectProperties.Builder.of(ReversedGravityEffect.class)
                    .id("reversed_gravity")
                    .supplier(ReversedGravityEffect::new)
                    .conflict(NoGravityEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public ReversedGravityEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickServer(ServerLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            var entities = level.getAllEntities();

            for (var entity : entities)
            {
                var gravity = entity.getGravity();

                if (gravity <= 0.0)
                {
                    continue;
                }

                var deltaMovement = entity.getDeltaMovement();
                entity.setDeltaMovement(deltaMovement.x, gravity, deltaMovement.z);
            }
        }

        super.onLevelTickServer(level, stage);
    }

    @Override
    public void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            var player = Minecraft.getInstance().player;

            if (player == null)
            {
                return;
            }

            var gravity = player.getGravity();

            if (gravity <= 0.0)
            {
                return;
            }

            var deltaMovement = player.getDeltaMovement();
            player.setDeltaMovement(deltaMovement.x, gravity, deltaMovement.z);
        }

        super.onLevelTickClient(level, stage);
    }
}
