package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BurnPlayersEffect extends InstantEffect
{
    public static final EffectProperties<BurnPlayersEffect> PROPERTIES =
            EffectProperties.Builder.of(BurnPlayersEffect.class)
                    .id("burn_players")
                    .supplier(BurnPlayersEffect::new)
                    .build();

    public BurnPlayersEffect()
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
            player.igniteForSeconds(8.0f);
        }
    }
}
