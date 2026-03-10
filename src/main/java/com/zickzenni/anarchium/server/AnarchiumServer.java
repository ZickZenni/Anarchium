package com.zickzenni.anarchium.server;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

public class AnarchiumServer
{
    private static final AnarchiumServer INSTANCE = new AnarchiumServer();

    private static final Logger LOGGER = LogUtils.getLogger();

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
}
