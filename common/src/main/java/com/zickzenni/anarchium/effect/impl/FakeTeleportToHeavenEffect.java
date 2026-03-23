package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class FakeTeleportToHeavenEffect extends InstantEffect
{
    public static final EffectProperties<FakeTeleportToHeavenEffect> PROPERTIES =
            EffectProperties.Builder.of(FakeTeleportToHeavenEffect.class)
                    .id("fake_teleport_to_heaven")
                    .supplier(FakeTeleportToHeavenEffect::new)
                    .build();

    private final Map<String, Vec3> positions;
    private boolean trolled;
    private int ticks;

    public FakeTeleportToHeavenEffect()
    {
        super(PROPERTIES.getId());
        this.positions = new HashMap<>();
        this.ticks = 100;
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
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
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
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
    public void onLevelTickServer(ILevelTickEvent<ServerLevel> event)
    {
        if (event.getStage() == ILevelTickEvent.Stage.POST)
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

        super.onLevelTickServer(event);
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
