package com.zickzenni.anarchium.client.effect;

import com.zickzenni.anarchium.effect.IEffectHandler;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class ClientReversedGravityEffect implements IEffectHandler
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
        player.setDeltaMovement(deltaMovement.x, gravity * 2, deltaMovement.z);
    }
}
