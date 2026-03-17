package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;

public class InvertVelocityEffect extends InstantEffect
{
    public static final EffectProperties<InvertVelocityEffect> PROPERTIES =
            EffectProperties.Builder.of(InvertVelocityEffect.class)
                    .id("invert_velocity")
                    .supplier(InvertVelocityEffect::new)
                    .build();

    public InvertVelocityEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            player.setDeltaMovement(player.getDeltaMovement().multiply(-1, -1, -1));
            player.hurtMarked = true;
        }
    }
}
