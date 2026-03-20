package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.util.LevelTickStage;
import com.zickzenni.anarchium.util.function.OnlinePlayersSupplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnarchiumServer implements OnlinePlayersSupplier<ServerPlayer>
{
    public static final ConfigValue<Integer> TIMER_DURATION = ConfigValue.newInteger("duration", 20 * 45);

    public AnarchiumServer()
    {
        if (Anarchium.getServer() != null)
        {
            throw new IllegalStateException("Server already initialized!");
        }
    }

    /**
     * Retrieves the server instance.
     */
    public static AnarchiumServer getInstance()
    {
        return Anarchium.getServer();
    }

    /**
     * Processes a tick update for the provided tick stage.
     * This method handles server effects.
     */
    public void processLevelTick(ServerLevel level, LevelTickStage stage)
    {
        /*
         * If there are no online players, skip the tick.
         */
        if (this.getOnlinePlayers().isEmpty())
        {
            return;
        }

        ServerEffectManager.tick(level, stage);
    }

    /**
     * Retrieves the minecraft server instance.
     */
    public MinecraftServer getMinecraftServer()
    {
        var server = ServerLifecycleHooks.getCurrentServer();
        Objects.requireNonNull(server);
        return server;
    }

    /**
     * Retrieves the online players.
     */
    @Override
    public List<ServerPlayer> getOnlinePlayers()
    {
        var server = ServerLifecycleHooks.getCurrentServer();
        return server != null ? server.getPlayerList().getPlayers() : new ArrayList<>();
    }
}
