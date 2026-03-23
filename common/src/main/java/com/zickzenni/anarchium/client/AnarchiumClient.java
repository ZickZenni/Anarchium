package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.client.util.ClientTimer;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import org.slf4j.Logger;

public class AnarchiumClient
{
    public static final Logger LOGGER = LogUtils.getLogger();

    private static AnarchiumClient INSTANCE;

    private final ClientTimer timer;
    private final AnarchiumGui gui;

    private AnarchiumClient()
    {
        this.timer = new ClientTimer();
        this.gui = new AnarchiumGui();
    }

    /**
     * Processes a tick update for the provided tick stage.
     * Depending on the tick stage, this method handles client effects and manages a prediction timer.
     */
    public void processLevelTick(ILevelTickEvent<ClientLevel> event)
    {
        ClientEffectManager.tick(event);

        if (event.getStage() == ILevelTickEvent.Stage.POST)
        {
            this.timer.tick();
        }
    }

    /**
     * Retrieves the timer.
     */
    public ClientTimer getTimer()
    {
        return this.timer;
    }

    public AnarchiumGui getGui()
    {
        return gui;
    }

    /**
     * Retrieves the client instance.
     */
    public static AnarchiumClient getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AnarchiumClient();
        }

        return INSTANCE;
    }
}
