package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.world.level.Level;

public interface IEffectImpl
{
    void onStart();

    void onEnd();

    void onLevelTick(Level level, LevelTickStage stage);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    default boolean hasIndefiniteEnded()
    {
        return false;
    }
}
