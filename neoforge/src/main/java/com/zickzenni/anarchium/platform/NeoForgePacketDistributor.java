package com.zickzenni.anarchium.platform;

import com.zickzenni.anarchium.platform.service.IPacketDistributor;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePacketDistributor implements IPacketDistributor
{
    @Override
    public void send(ServerPlayer player, CustomPacketPayload payload, CustomPacketPayload... payloads)
    {
        PacketDistributor.sendToPlayer(player, payload, payloads);
    }

    @Override
    public void broadcast(CustomPacketPayload payload, CustomPacketPayload... payloads)
    {
        PacketDistributor.sendToAllPlayers(payload, payloads);
    }
}
