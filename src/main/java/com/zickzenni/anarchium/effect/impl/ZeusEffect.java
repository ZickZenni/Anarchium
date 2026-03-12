package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ZeusEffect extends InstantEffect
{
    public static final EffectSupplier<ZeusEffect> SUPPLIER = ZeusEffect::new;

    public static final ResourceLocation ID = Anarchium.location("zeus");

    private boolean struck;

    private int ticks;

    public ZeusEffect()
    {
        super(ID);
        this.ticks = 100;
    }

    @Override
    public void onEndServer()
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            EntityType.LIGHTNING_BOLT.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.MOB_SUMMONED);
        }
    }

    @Override
    public void onLevelTickServer(ServerLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.POST)
        {
            if (this.ticks > 0)
            {
                this.ticks--;
            } else
            {
                this.onEndServer();
                this.struck = true;
            }
        }

        super.onLevelTickServer(level, stage);
    }

    @Override
    public void onEndClient()
    {
        this.struck = true;
        super.onEndClient();
    }

    @Override
    public boolean hasEnded()
    {
        return this.struck;
    }

    @Override
    public String getGUIName()
    {
        if (!this.struck)
        {
            return "";
        }

        return super.getGUIName();
    }
}
