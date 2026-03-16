package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;

public class DamagePlayersEffect extends InstantEffect
{
    public static final EffectProperties<DamagePlayersEffect> PROPERTIES =
            EffectProperties.Builder.of(DamagePlayersEffect.class)
                    .id("damage_players")
                    .supplier(DamagePlayersEffect::new)
                    .build();

    public DamagePlayersEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            player.hurt(player.damageSources().playerAttack(player), 2.0f);
        }
    }
}
