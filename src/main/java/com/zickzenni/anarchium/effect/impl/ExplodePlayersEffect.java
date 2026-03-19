package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.world.level.Level;

public class ExplodePlayersEffect extends InstantEffect
{
    public static final EffectProperties<ExplodePlayersEffect> PROPERTIES =
            EffectProperties.Builder.of(ExplodePlayersEffect.class)
                    .id("explode_players")
                    .supplier(ExplodePlayersEffect::new)
                    .build();

    public ExplodePlayersEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Anarchium.getServer().getOnlinePlayers())
        {
            var position = player.position();
            player.serverLevel().explode(null, position.x, position.y, position.z, 3, Level.ExplosionInteraction.TNT);
        }
    }
}
