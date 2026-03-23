package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.event.ILevelTickEvent;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import com.zickzenni.anarchium.event.IRenderLevelEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;

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

    default void onLevelTickServer(ILevelTickEvent<ServerLevel> event)
    {
    }

    default void onLevelTickClient(ILevelTickEvent<ClientLevel> event)
    {
    }

    default void onRenderLevel(IRenderLevelEvent event)
    {
    }

    default void onRenderGUI(IRenderGuiEvent event)
    {
    }

    boolean hasEnded();

    default void setTicks(int ticks)
    {
    }

    int getTicks();

    int getDurationTicks();

    ResourceLocation getLocation();

    String getGUIName();

    Holder<SoundEvent> getDispatchSound();
}
