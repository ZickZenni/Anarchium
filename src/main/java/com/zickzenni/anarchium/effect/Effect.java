package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

public interface Effect
{
    default void onStartServer()
    {
    }

    default void onEndServer()
    {
    }

    default void onStartClient()
    {
    }

    default void onEndClient()
    {
    }

    default void onLevelTickServer(ServerLevel level, LevelTickStage stage)
    {
    }

    default void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
    }

    default void onAfterLevelRender(float deltaTime)
    {
    }

    boolean hasEnded();

    int getTicks();

    default void setTicks(int ticks)
    {
    }

    int getDurationTicks();

    Identifier getIdentifier();

    String getGUIName();
}
