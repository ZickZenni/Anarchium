package com.zickzenni.anarchium.server.effect.impl;

import com.zickzenni.anarchium.effect.IEffectImpl;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ServerReversedGravityEffect implements IEffectImpl
{
    @Override
    public void onStart()
    {

    }

    @Override
    public void onEnd()
    {

    }

    @Override
    public void onLevelTick(Level level, LevelTickStage stage)
    {
        if (stage != LevelTickStage.POST)
        {
            return;
        }

        if (level.isClientSide())
        {
            return;
        }

        var serverLevel = (ServerLevel) level;
        var entities = serverLevel.getAllEntities();

        for (var entity : entities)
        {
            var gravity = entity.getGravity();

            if (gravity <= 0.0)
            {
                continue;
            }

            var deltaMovement = entity.getDeltaMovement();
            entity.setDeltaMovement(deltaMovement.x, gravity * 2, deltaMovement.z);
        }
    }
}
