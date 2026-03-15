package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.resources.ResourceLocation;

public class DropInventoryEffect extends InstantEffect
{
    public static final EffectSupplier<DropInventoryEffect> SUPPLIER = DropInventoryEffect::new;

    public static final ResourceLocation ID = Anarchium.location("drop_inventory");

    public DropInventoryEffect()
    {
        super(ID);
    }

    @Override
    public void onStartServer()
    {
        for (var player : AnarchiumServer.getPlayers())
        {
            player.getInventory().dropAll();
        }
    }
}
