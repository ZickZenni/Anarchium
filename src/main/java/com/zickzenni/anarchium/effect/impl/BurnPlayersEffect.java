package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BurnPlayersEffect extends InstantEffect
{
    public static final EffectSupplier<BurnPlayersEffect> SUPPLIER = BurnPlayersEffect::new;

    public static final Identifier ID = Anarchium.identifier("burn_players");

    public BurnPlayersEffect()
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
            player.igniteForSeconds(8.0f);
        }
    }
}
