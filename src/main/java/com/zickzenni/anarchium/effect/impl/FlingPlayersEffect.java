package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class FlingPlayersEffect extends InstantEffect
{
    public static final EffectSupplier<FlingPlayersEffect> SUPPLIER = FlingPlayersEffect::new;

    public static final Identifier ID = Anarchium.identifier("fling_players");

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
            var random = player.getRandom();
            var x = (random.nextDouble() * 2) - 1;
            var y = random.nextDouble() * 1.75;
            var z = (random.nextDouble() * 2) - 1;

            player.setDeltaMovement(x, y, z);
            player.hurtMarked = true;
        }
    }
}
