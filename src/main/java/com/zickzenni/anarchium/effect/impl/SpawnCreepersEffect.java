package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SpawnCreepersEffect extends InstantEffect
{
    public static final EffectProperties<SpawnCreepersEffect> PROPERTIES =
            EffectProperties.Builder.of(SpawnCreepersEffect.class)
                    .id("spawn_creepers")
                    .supplier(SpawnCreepersEffect::new)
                    .build();

    public SpawnCreepersEffect()
    {
        super(PROPERTIES.getId());
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
