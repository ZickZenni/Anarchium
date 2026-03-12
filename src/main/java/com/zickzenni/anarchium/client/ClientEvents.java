package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Anarchium.MODID, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void onPreLevelTick(LevelTickEvent.Pre event)
    {
        var level = event.getLevel();

        if (!level.isClientSide())
        {
            return;
        }

        AnarchiumClient.getInstance().processLevelTick((ClientLevel) level, LevelTickStage.PRE);
    }

    @SubscribeEvent
    public static void onPostLevelTick(LevelTickEvent.Post event)
    {
        var level = event.getLevel();

        if (!level.isClientSide())
        {
            return;
        }

        AnarchiumClient.getInstance().processLevelTick((ClientLevel) level, LevelTickStage.POST);
    }

    // ================ RENDER ================

    @SubscribeEvent
    public static void onAfterOpaqueBlocksRender(RenderLevelStageEvent event)
    {
        ClientEffectManager.sendRenderLevelStageEvent(event);
    }

    // ========================================

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event)
    {
        ClientEffectManager.clear();
        AnarchiumClient.getInstance().timerTicks = 0;
    }

    @SubscribeEvent
    public static void onPostRenderGui(RenderGuiEvent.Post event)
    {
        AnarchiumGUI.render(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onFOV(ViewportEvent.ComputeFov event)
    {
        if (EffectStates.enableCustomFOV)
        {
            event.setFOV(EffectStates.customFOV);
        }
    }
}
