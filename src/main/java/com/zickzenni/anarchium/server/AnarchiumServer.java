package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AnarchiumServer
{
    private static final AnarchiumServer INSTANCE = new AnarchiumServer();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ConfigValue<Integer> TIMER_DURATION = ConfigValue.newInteger("duration", 20 * 45);

    private AnarchiumServer() {}

    /**
     * Processes a level tick.
     */
    public void processLevelTick(ServerLevel level, LevelTickStage stage)
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        /*
         * Do not tick current effects or create new ones if there are no players.
         */
        if (server.getPlayerList().getPlayers().isEmpty())
        {
            return;
        }

        ServerEffectManager.tick(level, stage);
    }

    /**
     * Retrieves the instance.
     */
    public static AnarchiumServer getInstance()
    {
        return INSTANCE;
    }

    /**
     * Retrieves the list of players.
     */
    public static List<ServerPlayer> getPlayers()
    {
        var server = ServerLifecycleHooks.getCurrentServer();
        return server != null ? server.getPlayerList().getPlayers() : new ArrayList<>();
    }
}
