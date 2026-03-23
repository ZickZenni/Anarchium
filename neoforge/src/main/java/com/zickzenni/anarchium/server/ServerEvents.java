package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.AnarchiumMod;
import com.zickzenni.anarchium.Constants;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import com.zickzenni.anarchium.platform.Services;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public final class ServerEvents
{
    @SubscribeEvent
    public static void onStart(ServerStartingEvent event)
    {
        for (var property : EffectRegistry.getRegistry().values())
        {
            for (var value : property.getConfig())
            {
                value.load(AnarchiumMod.CONFIG);
            }
        }

        AnarchiumServer.TIMER_DURATION.load(AnarchiumMod.CONFIG);
        ServerEffectManager.timerTicks = AnarchiumServer.TIMER_DURATION.get();
    }

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

    private static void runLevelTick(LevelTickEvent forgeEvent, ILevelTickEvent.Stage stage)
    {
        var level = forgeEvent.getLevel();

        if (level.isClientSide() || !level.dimension().location().getPath().equals("overworld"))
        {
            return;
        }

        var event = new ILevelTickEvent<ServerLevel>()
        {
            @Override
            public ServerLevel getLevel()
            {
                return (ServerLevel) level;
            }

            @Override
            public Stage getStage()
            {
                return stage;
            }
        };

        AnarchiumServer.getInstance().processLevelTick(event);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity().level().isClientSide())
        {
            return;
        }

        Services.PACKET_DISTRIBUTOR.send((ServerPlayer) event.getEntity(), EffectRegistry.createConfigurationPacket());
    }
}