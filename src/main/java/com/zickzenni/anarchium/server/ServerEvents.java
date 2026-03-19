package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.network.packet.ConfigurationPacket;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Anarchium.MODID)
public final class ServerEvents
{
    @SubscribeEvent
    public static void onStart(ServerStartingEvent event)
    {
        for (var property : EffectRegistry.getRegistry().values())
        {
            for (var value : property.getConfig())
            {
                value.load();
            }
        }

        AnarchiumServer.TIMER_DURATION.load();
        ServerEffectManager.timerTicks = AnarchiumServer.TIMER_DURATION.get();
    }

    @SubscribeEvent
    public static void onPreLevelTick(LevelTickEvent.Pre event)
    {
        var level = event.getLevel();

        if (level.isClientSide())
        {
            return;
        }

        AnarchiumServer.getInstance().processLevelTick((ServerLevel) level, LevelTickStage.PRE);
    }

    @SubscribeEvent
    public static void onPostLevelTick(LevelTickEvent.Post event)
    {
        var level = event.getLevel();

        if (level.isClientSide() || !level.dimension().location().getPath().equals("overworld"))
        {
            return;
        }

        AnarchiumServer.getInstance().processLevelTick((ServerLevel) level, LevelTickStage.POST);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        //noinspection resource
        if (event.getEntity().level().isClientSide())
        {
            return;
        }

        List<ConfigurationPacket.Effect> effects = getEffects();
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new ConfigurationPacket(effects));
    }

    private static @NonNull List<ConfigurationPacket.Effect> getEffects()
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
