package com.zickzenni.anarchium.effect;

import net.minecraft.world.level.Level;

public abstract class BaseEffect
{
    public final EffectDescription description;

    private int ticks;

    public BaseEffect(EffectDescription description)
    {
        this.description = description;
        this.ticks = description.ticks;
    }

    public void onPick(Level level)
    {
    }

    public final void onLevelTick(Level level, boolean pre)
    {
        if (ticks <= 0)
        {
            return;
        }

        if (pre)
            onLevelTickPre(level);
        else
        {
            onLevelTickPost(level);
            ticks--;
        }
    }

    public void onLevelTickPre(Level level)
    {
    }

    public void onLevelTickPost(Level level)
    {
    }

    public void resetTicks()
    {
        ticks = description.ticks;
    }
}
