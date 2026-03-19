package com.zickzenni.anarchium.client.util;

import com.zickzenni.anarchium.network.packet.TimerTickPacket;
import net.minecraft.util.Mth;

public class ClientTimer
{
    private int ticks;
    private int duration;

    /**
     * Updates the timer by decrementing the tick count.
     * If the tick count reaches zero or below, the timer is reset to its initial duration.
     */
    public void tick()
    {
        this.ticks--;

        if (this.ticks <= 0)
        {
            this.reset();
        }
    }

    /**
     * Synchronizes the client timer with the data given by the server.
     */
    public void synchronize(TimerTickPacket packet)
    {
        if (this.duration != packet.duration())
        {
            this.duration = packet.duration();
            this.reset();
        }

        if (Math.abs(packet.ticks() - this.ticks) <= 5)
        {
            this.ticks = Mth.lerpInt(0.5f, this.ticks, packet.ticks());
        } else
        {
            this.ticks = packet.ticks();
        }
    }

    /**
     * Retrieves the ticks remaining in the timer.
     */
    public int getTicks()
    {
        return this.ticks;
    }

    /**
     * Retrieves the duration of the timer.
     */
    public int getDuration()
    {
        return this.duration;
    }

    /**
     * Sets the duration of the timer.
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Resets the timer to its initial duration.
     */
    public void reset()
    {
        this.ticks = this.duration;
    }
}
