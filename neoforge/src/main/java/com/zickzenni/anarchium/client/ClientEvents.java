package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Constants;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.effect.impl.EveryoneIsAVillagerEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import com.zickzenni.anarchium.event.IRenderLevelEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class ClientEvents
{
    @SubscribeEvent
    public static void onPreLevelTick(LevelTickEvent.Pre event)
    {
        runLevelTick(event, ILevelTickEvent.Stage.PRE);
    }

    @SubscribeEvent
    public static void onPostLevelTick(LevelTickEvent.Post event)
    {
        runLevelTick(event, ILevelTickEvent.Stage.POST);
    }

    @SubscribeEvent
    public static void onPreRenderGui(RenderGuiEvent.Post event)
    {
        runRenderGui(event, IRenderGuiEvent.Stage.PRE);
    }

    @SubscribeEvent
    public static void onPostRenderGui(RenderGuiEvent.Post event)
    {
        runRenderGui(event, IRenderGuiEvent.Stage.POST);
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event)
    {
        ClientEffectManager.clear();
        AnarchiumClient.getInstance().getTimer().setDuration(Integer.MAX_VALUE);
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
        EveryoneIsAVillagerEffect.VILLAGER_MODEL =
                new VillagerModel<>(event.getContext().bakeLayer(ModelLayers.VILLAGER));
    }

    @SubscribeEvent
    public static void onAfterOpaqueBlocksRender(RenderLevelStageEvent forgeEvent)
    {
        var event = new IRenderLevelEvent()
        {
            @Override
            public Stage getStage()
            {
                if (forgeEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY)
                {
                    return Stage.START;
                } else if (forgeEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES)
                {
                    return Stage.AFTER_ENTITIES;
                } else if (forgeEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)
                {
                    return Stage.AFTER_TRANSLUCENT;
                } else if (forgeEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL)
                {
                    return Stage.LAST;
                } else
                {
                    return Stage.UNKNOWN;
                }
            }

            @Override
            public DeltaTracker getPartialTick()
            {
                return forgeEvent.getPartialTick();
            }
        };

        ClientEffectManager.dispatchRenderLevelStageEvent(event);
    }


    private static void runLevelTick(LevelTickEvent forgeEvent, ILevelTickEvent.Stage stage)
    {
        var level = forgeEvent.getLevel();

        if (!level.isClientSide())
        {
            return;
        }

        var event = new ILevelTickEvent<ClientLevel>()
        {
            @Override
            public ClientLevel getLevel()
            {
                return (ClientLevel) level;
            }

            @Override
            public Stage getStage()
            {
                return stage;
            }
        };

        AnarchiumClient.getInstance().processLevelTick(event);
    }

    private static void runRenderGui(RenderGuiEvent forgeEvent, IRenderGuiEvent.Stage stage)
    {
        var event = new IRenderGuiEvent()
        {
            @Override
            public GuiGraphics getGraphics()
            {
                return forgeEvent.getGuiGraphics();
            }

            @Override
            public DeltaTracker getPartialTick()
            {
                return forgeEvent.getPartialTick();
            }

            @Override
            public Stage getStage()
            {
                return stage;
            }
        };

        ClientEffectManager.dispatchRenderGuiEvent(event);
        AnarchiumClient.getInstance().getGui().render(event);
    }
}