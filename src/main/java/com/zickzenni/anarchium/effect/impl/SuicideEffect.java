package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SuicideEffect extends InstantEffect
{
    public static final EffectSupplier<SuicideEffect> SUPPLIER = SuicideEffect::new;

    public static final ResourceLocation ID = Anarchium.location("suicide");

    public SuicideEffect()
    {
        super(ID);
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
