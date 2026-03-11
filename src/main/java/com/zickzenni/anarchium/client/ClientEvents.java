package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Anarchium.MODID, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterClientPayloadHandlersEvent event)
    {
        event.register(ActivateEffectPacket.TYPE, ClientPayloadHandler::handleActivateEffect);
        event.register(EndEffectPacket.TYPE, ClientPayloadHandler::handleEndEffect);
        event.register(TickEffectPacket.TYPE, ClientPayloadHandler::handleTickEffect);
        event.register(TimerTickPacket.TYPE, ClientPayloadHandler::handleTimerTick);
    }

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

    @SubscribeEvent
    public static void onAfterLevelRender(RenderLevelStageEvent.AfterLevel event)
    {
        ClientEffectManager.sendAfterLevelRender();
    }

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
