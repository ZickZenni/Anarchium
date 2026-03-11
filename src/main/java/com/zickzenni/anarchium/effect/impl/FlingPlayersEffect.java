package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class FlingPlayersEffect extends InstantEffect
{
    public static final EffectSupplier<FlingPlayersEffect> SUPPLIER = FlingPlayersEffect::new;

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Anarchium.MODID, "fling_players");

    public FlingPlayersEffect()
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
            player.setDeltaMovement(0, 1.5, 0);
            player.hurtMarked = true;
        }
    }
}
