package com.zickzenni.anarchium.effect.base;

import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class TimedEffect extends BaseEffect
{
    private final int durationTicks;

    private int ticks;

    private boolean ended;

    public TimedEffect(ResourceLocation location, int durationTicks)
    {
        super(location);
        this.durationTicks = durationTicks;
        this.ticks = durationTicks;
    }

    @Override
    public void onLevelTickServer(ILevelTickEvent<ServerLevel> event)
    {
        if (event.getStage() != ILevelTickEvent.Stage.POST)
        {
            return;
        }

        if (this.ticks > 0)
        {
            this.ticks--;
            return;
        }

        this.ended = true;
        this.onEndServer();
    }

    @Override
    public boolean hasEnded()
    {
        return this.ended;
    }

    @Override
    public int getTicks()
    {
        return this.ticks;
    }

    @Override
    public void setTicks(int ticks)
    {
        this.ticks = ticks;

        if (this.ticks > 0)
        {
            this.ended = false;
        }
    }

    @Override
    public int getDurationTicks()
    {
        return this.durationTicks;
    }
}
