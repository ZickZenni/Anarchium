package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import net.minecraft.server.level.ServerLevel;

public class AnarchiumServer
{
    public static final ConfigValue<Integer> TIMER_DURATION = ConfigValue.newInteger("timer.duration", 20 * 45);

    private static AnarchiumServer INSTANCE;

    private AnarchiumServer()
    {
    }

    /**
     * Processes a tick update for the provided tick stage.
     * This method handles server effects.
     */
    public void processLevelTick(ILevelTickEvent<ServerLevel> event)
    {
        ServerEffectManager.tick(event);
    }

    /**
     * Retrieves the server instance.
     */
    public static AnarchiumServer getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AnarchiumServer();
        }

        return INSTANCE;
    }
}
