package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSources;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class DamagePlayersEffect extends InstantEffect
{
    public static final EffectSupplier<DamagePlayersEffect> SUPPLIER = DamagePlayersEffect::new;

    public static final ResourceLocation ID = Anarchium.location("damage_players");

    public DamagePlayersEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            player.hurt(player.damageSources().playerAttack(player), 2.0f);
        }
    }
}
