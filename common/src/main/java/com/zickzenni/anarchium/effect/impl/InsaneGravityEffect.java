package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;

public class InsaneGravityEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);
    public static final ConfigValue<Double> MULTIPLIER =
            ConfigValue.newDoubleInRange("gravity_multiplier", 5.0, 0.0, 250.0);

    public InsaneGravityEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static final EffectProperties<InsaneGravityEffect> PROPERTIES =
            EffectProperties.Builder.of(InsaneGravityEffect.class)
                    .id("insane_gravity")
                    .supplier(InsaneGravityEffect::new)
                    .conflict(NoGravityEffect.class)
                    .conflict(ReversedGravityEffect.class)
                    .config(DURATION)
                    .config(MULTIPLIER)
                    .build();

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
                var fall = deltaMovement.y - (gravity * (MULTIPLIER.get().floatValue() - 1));
                entity.setDeltaMovement(deltaMovement.x, fall, deltaMovement.z);
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
            var fall = deltaMovement.y - (gravity * (MULTIPLIER.get().floatValue() - 1));
            player.setDeltaMovement(deltaMovement.x, fall, deltaMovement.z);
        }

        super.onLevelTickClient(event);
    }
}
