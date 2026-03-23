package com.zickzenni.anarchium.effect.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public abstract class BasePotionEffectEffect extends InstantEffect
{
    public BasePotionEffectEffect(ResourceLocation location)
    {
        super(location);
    }

    @Override
    public void onStartServer()
    {
//        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
//        {
//            player.addEffect(getPotionEffect(player));
//        }
    }

    protected abstract MobEffectInstance getPotionEffect(ServerPlayer player);
}
