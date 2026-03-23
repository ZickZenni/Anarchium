package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;

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
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            player.igniteForSeconds(8.0f);
        }
    }
}
