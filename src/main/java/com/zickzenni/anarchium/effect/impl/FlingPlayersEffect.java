package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class FlingPlayersEffect extends InstantEffect
{
    public static final EffectProperties<FlingPlayersEffect> PROPERTIES =
            EffectProperties.Builder.of(FlingPlayersEffect.class)
                    .id("fling_players")
                    .supplier(FlingPlayersEffect::new)
                    .build();

    public FlingPlayersEffect()
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
            var x = (random.nextDouble() * 2) - 1;
            var y = random.nextDouble() * 1.75;
            var z = (random.nextDouble() * 2) - 1;

            player.setDeltaMovement(x, y, z);
            player.hurtMarked = true;
        }
    }
}
