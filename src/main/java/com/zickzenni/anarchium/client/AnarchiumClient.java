package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.util.LevelTickStage;
import com.zickzenni.anarchium.util.OnlinePlayersSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;

import java.util.List;

public class AnarchiumClient implements OnlinePlayersSupplier<AbstractClientPlayer>
{
    private final AnarchiumGui gui;

    private int timerTicks;
    private int timerDuration;
    public int predictTicks;

    public AnarchiumClient()
    {
        if (Anarchium.getClient() != null)
        {
            throw new IllegalStateException("Client already initialized!");
        }

        this.gui = new AnarchiumGui();
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

    /**
     * Retrieves the ticks remaining in the timer.
     */
    public int getTimerTicks()
    {
        return this.timerTicks;
    }

    /**
     * Sets the ticks remaining in the timer.
     */
    public void setTimerTicks(int ticks)
    {
        this.timerTicks = ticks;
    }

    /**
     * Retrieves the duration of the timer.
     */
    public int getTimerDuration()
    {
        return this.timerDuration;
    }

    /**
     * Sets the duration of the timer.
     *
     * @param duration The duration of the timer.
     */
    public void setTimerDuration(int duration)
    {
        this.timerDuration = duration;
    }

    /**
     * Resets the timer to its initial duration.
     */
    public void resetTimer()
    {
        this.timerTicks = this.timerDuration;
    }

    /**
     * Retrieves the gui.
     */
    public AnarchiumGui getGui()
    {
        return this.gui;
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
