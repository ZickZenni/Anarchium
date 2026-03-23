package com.zickzenni.anarchium.platform.service;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

/**
 * Interface for distributing packet payloads to clients or the server.
 */
public interface IPacketDistributor
{
    /**
     * Sends one or more packet payloads to a specific server player.
     *
     * @param player   The server player to whom the packet(s) should be sent.
     * @param payload  The primary packet payload to be sent.
     * @param payloads Additional packet payloads to be sent.
     */
    void send(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... payloads);

    /**
     * Sends one or more packet payloads to all connected server players.
     *
     * @param payload  The primary packet payload to be sent.
     * @param payloads Additional packet payloads to be sent.
     */
    void broadcast(CustomPacketPayload payload, CustomPacketPayload... payloads);
}
