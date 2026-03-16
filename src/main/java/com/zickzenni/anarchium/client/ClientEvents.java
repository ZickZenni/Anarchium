package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.impl.EveryoneIsAVillagerEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
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

    @SubscribeEvent
    public static void onPostRenderGui(RenderGuiEvent.Post event)
    {
        ClientEffectManager.sendRenderGuiEvent(event);
        AnarchiumClient.getInstance().getGui().render(event);
    }

    // ========================================

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event)
    {
        ClientEffectManager.clear();
        AnarchiumClient.getInstance().timerTicks = 0;
    }

    @SubscribeEvent
    public static void onFOV(ViewportEvent.ComputeFov event)
    {
        if (EffectStates.enableCustomFOV)
        {
            event.setFOV(EffectStates.customFOV);
        }
    }

    @SubscribeEvent
    public static void onAddEntityLayers(EntityRenderersEvent.AddLayers event)
    {
        EveryoneIsAVillagerEffect.VILLAGER_MODEL = new VillagerModel<>(event.getContext().bakeLayer(ModelLayers.VILLAGER));
    }
}
