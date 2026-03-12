package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class ReversedGravityEffect extends TimedEffect
{
    public static final EffectSupplier<ReversedGravityEffect> SUPPLIER = ReversedGravityEffect::new;

    public static final ResourceLocation ID = Anarchium.location("reversed_gravity");

    public ReversedGravityEffect()
    {
        super(ID, 220);
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
