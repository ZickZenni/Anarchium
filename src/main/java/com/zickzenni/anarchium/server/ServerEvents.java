package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Anarchium.MODID)
public final class ServerEvents
{
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

        if (level.isClientSide() || !level.dimension().identifier().getPath().equals("overworld"))
        {
            return;
        }

        AnarchiumServer.getInstance().processLevelTick((ServerLevel) level, LevelTickStage.POST);
    }
}
