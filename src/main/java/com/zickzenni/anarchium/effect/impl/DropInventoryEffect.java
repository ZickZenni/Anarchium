package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;

public class DropInventoryEffect extends InstantEffect
{
    public static final EffectProperties<DropInventoryEffect> PROPERTIES =
            EffectProperties.Builder.of(DropInventoryEffect.class)
                    .id("drop_inventory")
                    .supplier(DropInventoryEffect::new)
                    .build();

    public DropInventoryEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            player.getInventory().dropAll();
        }
    }
}
