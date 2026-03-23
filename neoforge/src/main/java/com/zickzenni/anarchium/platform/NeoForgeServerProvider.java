package com.zickzenni.anarchium.platform;

import com.zickzenni.anarchium.platform.service.IServerProvider;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class NeoForgeServerProvider implements IServerProvider
{
    @Override
    public MinecraftServer getServer()
    {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
