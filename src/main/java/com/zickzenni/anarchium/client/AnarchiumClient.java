package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.util.ClientTimer;
import com.zickzenni.anarchium.util.LevelTickStage;
import com.zickzenni.anarchium.util.OnlinePlayersSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;

import java.util.List;

public class AnarchiumClient implements OnlinePlayersSupplier<AbstractClientPlayer>
{
    private final AnarchiumGui gui;
    private final ClientTimer timer;

    public AnarchiumClient()
    {
        if (Anarchium.getClient() != null)
        {
            throw new IllegalStateException("Client already initialized!");
        }

        this.gui = new AnarchiumGui();
        this.timer = new ClientTimer();
    }

    /**
     * Retrieves the client instance.
     */
    public static AnarchiumClient getInstance()
    {
        return Anarchium.getClient();
    }

    /**
     * Processes a tick update for the provided tick stage.
     * Depending on the tick stage, this method handles client effects and manages a prediction timer.
     */
    public void processLevelTick(ClientLevel level, LevelTickStage stage)
    {
        ClientEffectManager.tick(level, stage);

        if (stage == LevelTickStage.POST)
        {
            this.timer.tick();
        }
    }

    /**
     * Retrieves the gui.
     */
    public AnarchiumGui getGui()
    {
        return this.gui;
    }

    /**
     * Retrieves the timer.
     */
    public ClientTimer getTimer()
    {
        return this.timer;
    }

    /**
     * Retrieves the online players in the current world.
     */
    @Override
    public List<AbstractClientPlayer> getOnlinePlayers()
    {
        var minecraft = Minecraft.getInstance();
        var level = minecraft.level;

        if (level == null)
        {
            return List.of();
        }

        return level.players();
    }
}
