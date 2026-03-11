package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SpawnCreepersEffect extends InstantEffect
{
    public static final EffectSupplier<SpawnCreepersEffect> SUPPLIER = SpawnCreepersEffect::new;

    public static final Identifier ID = Anarchium.identifier("spawn_creepers");

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
                EntityType.CREEPER.spawn(player.level(), player.blockPosition(), EntitySpawnReason.NATURAL);
            }
        }
    }
}
