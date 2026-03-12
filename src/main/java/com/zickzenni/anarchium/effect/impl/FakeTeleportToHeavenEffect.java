package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

public class FakeTeleportToHeavenEffect extends InstantEffect
{
    public static final EffectSupplier<FakeTeleportToHeavenEffect> SUPPLIER = FakeTeleportToHeavenEffect::new;

    public static final ResourceLocation ID = Anarchium.location("fake_teleport_to_heaven");

    private final Map<String, Vec3> positions;

    private boolean trolled;

    private int ticks;

    public FakeTeleportToHeavenEffect()
    {
        super(ID);
        this.positions = new HashMap<>();
        this.ticks = 100;
    }

    @Override
    public void onStartServer()
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            var position = player.position();

            positions.put(player.getUUID().toString(), position);

            player.setDeltaMovement(0, 0, 0);
            player.teleportTo(position.x, TeleportToHeavenEffect.HEIGHT, position.z);
            player.hurtMarked = true;
        }
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
            if (positions.containsKey(player.getUUID().toString()))
            {
                var position = positions.get(player.getUUID().toString());
                player.setDeltaMovement(0, 0, 0);
                player.resetFallDistance();
                player.teleportTo(position.x, position.y, position.z);
                player.hurtMarked = true;
            }
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
                this.trolled = true;
            }
        }

        super.onLevelTickServer(level, stage);
    }

    @Override
    public void onEndClient()
    {
        this.trolled = true;
        super.onEndClient();
    }

    @Override
    public boolean hasEnded()
    {
        return this.trolled;
    }

    @Override
    public String getGUIName()
    {
        if (!this.trolled)
        {
            return Component.translatable("anarchium.teleport_to_heaven").getString();
        }

        return super.getGUIName();
    }
}
