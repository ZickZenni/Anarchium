package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SpawnCreepersEffect extends InstantEffect
{
    public static final EffectSupplier<SpawnCreepersEffect> SUPPLIER = SpawnCreepersEffect::new;

    public static final ResourceLocation ID = Anarchium.location("spawn_creepers");

    public SpawnCreepersEffect()
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
            var random = player.getRandom();

            for (int i = 0; i < random.nextIntBetweenInclusive(2, 4); i++)
            {
                EntityType.CREEPER.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.MOB_SUMMONED);
            }
        }
    }
}
