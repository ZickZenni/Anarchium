package com.zickzenni.anarchium.util.function;

import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface OnlinePlayersSupplier<T extends Player>
{
    /**
     * Retrieves all or partial online players.
     */
    List<T> getOnlinePlayers();
}
