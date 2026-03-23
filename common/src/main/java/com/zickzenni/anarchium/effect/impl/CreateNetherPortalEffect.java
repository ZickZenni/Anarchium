package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.core.Direction;

public class CreateNetherPortalEffect extends InstantEffect
{
    public static final EffectProperties<CreateNetherPortalEffect> PROPERTIES =
            EffectProperties.Builder.of(CreateNetherPortalEffect.class)
                    .id("create_nether_portal")
                    .supplier(CreateNetherPortalEffect::new)
                    .build();

    public CreateNetherPortalEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            player.serverLevel().getPortalForcer().createPortal(player.blockPosition(), Direction.Axis.X);
        }
    }
}
