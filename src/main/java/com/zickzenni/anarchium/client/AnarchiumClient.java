package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import org.slf4j.Logger;

public class AnarchiumClient
{
    private static final AnarchiumClient INSTANCE = new AnarchiumClient();

    public static final Logger LOGGER = LogUtils.getLogger();

    public int timerTicks;

    public int timerDuration;

    public int predictTicks;

    private AnarchiumClient()
    {

    }

    public void processLevelTick(ClientLevel level, LevelTickStage stage)
    {
        ClientEffectManager.tick(level, stage);

        if (stage == LevelTickStage.POST)
        {
            if (predictTicks <= 0)
            {
                return;
            }

            predictTicks--;

            if (timerTicks > 0)
            {
                timerTicks--;
            } else
            {
                timerTicks = timerDuration;
            }
        }
    }

    public static AnarchiumClient getInstance()
    {
        return INSTANCE;
    }
}
