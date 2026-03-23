package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.AnarchiumMod;
import com.zickzenni.anarchium.Constants;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import com.zickzenni.anarchium.network.ConfigurationPacket;
import com.zickzenni.anarchium.platform.Services;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.List;

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

        List<ConfigurationPacket.Effect> effects = getEffects();
        Services.PACKET_DISTRIBUTOR.send((ServerPlayer) event.getEntity(), new ConfigurationPacket(effects));
    }

    private static List<ConfigurationPacket.Effect> getEffects()
    {
        var properties = EffectRegistry.getRegistry();

        List<ConfigurationPacket.Effect> effects = new ArrayList<>();

        for (var property : properties.values())
        {
            if (property.getConfig().isEmpty())
            {
                continue;
            }

            List<ConfigurationPacket.Entry> entries = new ArrayList<>();

            for (ConfigValue<?> value : property.getConfig())
            {
                if (value.getName().equals("enabled") || value.getName().equals("weight"))
                {
                    continue;
                }

                entries.add(new ConfigurationPacket.Entry(value.getName(), value.getType(), value.getRaw()));
            }

            effects.add(new ConfigurationPacket.Effect(property.getId().toString(), entries));
        }

        return effects;
    }
}