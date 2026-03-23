package com.zickzenni.anarchium.platform;

import com.zickzenni.anarchium.platform.service.IPlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;

public class NeoForgePlayerProvider implements IPlayerProvider
{
    @Override
    public List<ServerPlayer> getServerPlayers()
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return List.of();
        }

        return server.getPlayerList().getPlayers();
    }

    @Override
    public List<AbstractClientPlayer> getClientPlayers()
    {
        var minecraft = Minecraft.getInstance();
        var level = minecraft.level;

        if (level == null)
        {
            return List.of();
        }

        return level.players();
    }
}
