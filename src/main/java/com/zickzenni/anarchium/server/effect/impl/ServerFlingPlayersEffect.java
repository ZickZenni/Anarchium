package com.zickzenni.anarchium.server.effect.impl;

import com.zickzenni.anarchium.effect.IEffectImpl;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerFlingPlayersEffect implements IEffectImpl
{
    @Override
    public void onStart()
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (var player : server.getPlayerList().getPlayers())
        {
            player.setDeltaMovement(0, 1.5, 0);
            player.hurtMarked = true;
        }
    }

    @Override
    public void onEnd()
    {

    }

    @Override
    public void onLevelTick(Level level, LevelTickStage stage)
    {

    }
}
