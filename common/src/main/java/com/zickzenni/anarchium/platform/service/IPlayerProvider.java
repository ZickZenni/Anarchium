package com.zickzenni.anarchium.platform.service;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

/**
 * Interface for providing player information.
 */
public interface IPlayerProvider
{
    /**
     * Retrieves a list of all players currently connected on the server.
     *
     * @return A list of {@link ServerPlayer} objects representing the connected server players.
     */
    List<ServerPlayer> getServerPlayers();

    /**
     * Retrieves a list of all players on the client.
     *
     * @return A list of {@link AbstractClientPlayer} objects representing the client players.
     */
    List<AbstractClientPlayer> getClientPlayers();
}
