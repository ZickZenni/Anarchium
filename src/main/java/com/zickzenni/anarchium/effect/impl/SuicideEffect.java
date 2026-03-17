package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

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
        var server = ServerLifecycleHooks.getCurrentServer();

        if (server == null)
        {
            return;
        }

        for (var player : server.getPlayerList().getPlayers())
        {
            player.kill();
        }
    }
}
