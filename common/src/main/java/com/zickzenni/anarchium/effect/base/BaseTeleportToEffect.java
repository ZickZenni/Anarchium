package com.zickzenni.anarchium.effect.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public abstract class BaseTeleportToEffect extends InstantEffect
{
    public BaseTeleportToEffect(ResourceLocation location)
    {
        super(location);
    }

    @Override
    public void onStartServer()
    {
//        var server = ServerLifecycleHooks.getCurrentServer();
//
//        if (server == null)
//        {
//            return;
//        }
//
//        for (ServerPlayer player : server.getPlayerList().getPlayers())
//        {
//            var position = getTeleportPosition(player);
//            player.setDeltaMovement(0, 0, 0);
//            player.teleportTo(position.x, position.y, position.z);
//            player.hurtMarked = true;
//        }
    }

    protected abstract Vec3 getTeleportPosition(ServerPlayer player);
}
