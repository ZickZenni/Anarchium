package com.zickzenni.anarchium.server.effect;

import com.zickzenni.anarchium.effect.IEffectHandler;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ServerReversedGravityEffect implements IEffectHandler
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
        if (stage != LevelTickStage.Post)
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
