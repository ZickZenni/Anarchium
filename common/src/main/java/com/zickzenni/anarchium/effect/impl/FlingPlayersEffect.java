package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;

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
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
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
