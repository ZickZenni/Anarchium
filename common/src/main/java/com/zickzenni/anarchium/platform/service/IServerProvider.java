package com.zickzenni.anarchium.platform.service;

import net.minecraft.server.MinecraftServer;

/**
 * Interface for providing server information.
 */
public interface IServerProvider
{
    /**
     * Retrieves the instance of the Minecraft server.
     *
     * @return The {@link MinecraftServer} instance representing the current server.
     */
    MinecraftServer getServer();
}
