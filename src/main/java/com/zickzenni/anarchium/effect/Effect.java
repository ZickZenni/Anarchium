package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

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

    default void onRenderLevel(RenderLevelStageEvent event, float deltaTime)
    {
    }

    default void onRenderGUI(GuiGraphics graphics)
    {
    }

    boolean hasEnded();

    int getTicks();

    default void setTicks(int ticks)
    {
    }

    int getDurationTicks();

    ResourceLocation getLocation();

    String getGUIName();

    Holder<SoundEvent> getDispatchSound();
}
