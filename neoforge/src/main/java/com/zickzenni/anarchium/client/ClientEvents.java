package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.Constants;
import com.zickzenni.anarchium.client.effect.ClientEffectManager;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import com.zickzenni.anarchium.effect.event.IRenderGuiEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
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

        ClientEffectManager.sendRenderGuiEvent(event);
        AnarchiumClient.getInstance().getGui().render(event);
    }
}