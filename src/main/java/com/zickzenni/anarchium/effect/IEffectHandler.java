package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.world.level.Level;

public interface IEffectHandler
{
    void onStart();

    void onEnd();

    void onLevelTick(Level level, LevelTickStage stage);
}
