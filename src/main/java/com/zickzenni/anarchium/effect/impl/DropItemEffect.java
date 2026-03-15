package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;

public class DropItemEffect extends InstantEffect
{
    public static final EffectSupplier<DropItemEffect> SUPPLIER = DropItemEffect::new;

    public static final ResourceLocation ID = Anarchium.location("drop_item");

    public DropItemEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            player.drop(true);
        }
    }
}
