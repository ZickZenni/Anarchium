package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;

public class DropItemEffect extends InstantEffect
{
    public static final EffectProperties<DropItemEffect> PROPERTIES =
            EffectProperties.Builder.of(DropItemEffect.class)
                    .id("drop_item")
                    .supplier(DropItemEffect::new)
                    .build();

    public DropItemEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            player.drop(true);
        }
    }
}
