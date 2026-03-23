package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
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

    public ReversedGravityEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickServer(ILevelTickEvent<ServerLevel> event)
    {
        if (event.getStage() == ILevelTickEvent.Stage.POST)
        {
            var entities = event.getLevel().getAllEntities();

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

        super.onLevelTickServer(event);
    }

    @Override
    public void onLevelTickClient(ILevelTickEvent<ClientLevel> event)
    {
        if (event.getStage() == ILevelTickEvent.Stage.POST)
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

        super.onLevelTickClient(event);
    }
}
