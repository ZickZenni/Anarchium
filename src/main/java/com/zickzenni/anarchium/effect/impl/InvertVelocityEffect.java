package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;

public class InvertVelocityEffect extends InstantEffect
{
    public static final EffectSupplier<InvertVelocityEffect> SUPPLIER = InvertVelocityEffect::new;

    public static final ResourceLocation ID = Anarchium.location("invert_velocity");

    public static final float RADIUS = 50;

    public InvertVelocityEffect()
    {
        super(ID);
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
