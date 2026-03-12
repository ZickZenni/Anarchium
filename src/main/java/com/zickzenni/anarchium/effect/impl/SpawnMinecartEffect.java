package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

public class SpawnMinecartEffect extends InstantEffect
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final EffectSupplier<SpawnMinecartEffect> SUPPLIER = SpawnMinecartEffect::new;

    public static final ResourceLocation ID = Anarchium.location("spawn_minecart");

    public SpawnMinecartEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (var player : server.getPlayerList().getPlayers())
        {
            if (player.getVehicle() != null && player.getVehicle().getType() == EntityType.MINECART)
            {
                continue;
            }

            var minecart = EntityType.MINECART.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.NATURAL);

            if (minecart == null)
            {
                LOGGER.error("Failed to spawn minecart for player {}", player.getDisplayName());
                continue;
            }

            player.startRiding(minecart);
        }
    }
}
