package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ExplodePlayersEffect extends InstantEffect
{
    public static final EffectSupplier<ExplodePlayersEffect> SUPPLIER = ExplodePlayersEffect::new;

    public static final ResourceLocation ID = Anarchium.location("explode_players");

    public ExplodePlayersEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            var position = player.position();
            player.serverLevel().explode(null, position.x, position.y, position.z, 3, Level.ExplosionInteraction.TNT);
        }
    }
}
