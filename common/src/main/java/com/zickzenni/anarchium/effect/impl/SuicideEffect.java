package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;

public class SuicideEffect extends InstantEffect
{
    public static final EffectProperties<SuicideEffect> PROPERTIES =
            EffectProperties.Builder.of(SuicideEffect.class)
                    .id("suicide")
                    .supplier(SuicideEffect::new)
                    .build();

    public SuicideEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            player.kill();
        }
    }
}
