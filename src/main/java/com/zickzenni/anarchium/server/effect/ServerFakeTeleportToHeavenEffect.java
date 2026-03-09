package com.zickzenni.anarchium.server.effect;

import com.zickzenni.anarchium.effect.IEffectHandler;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

public class ServerFakeTeleportToHeavenEffect implements IEffectHandler
{
    private int ticks;

    private final Map<String, Vec3> positions;

    public ServerFakeTeleportToHeavenEffect()
    {
        this.ticks = 20 * 8;
        this.positions = new HashMap<>();
    }

    @Override
    public void onStart()
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

            player.teleportTo(position.x, position.y + 20000, position.z);
        }
    }

    @Override
    public void onEnd()
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
                player.resetFallDistance();
                player.teleportTo(position.x, position.y, position.z);
            }
        }
    }

    @Override
    public void onLevelTick(Level level, LevelTickStage stage)
    {
        if (stage != LevelTickStage.Post)
        {
            return;
        }

        if (level.isClientSide())
        {
            return;
        }

        if (ticks > 0)
        {
            ticks--;
        }
    }

    @Override
    public boolean hasIndefiniteEnded()
    {
        return ticks <= 0;
    }
}
