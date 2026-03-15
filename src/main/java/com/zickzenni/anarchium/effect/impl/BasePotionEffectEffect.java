package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
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
        for (var player : AnarchiumServer.getPlayers()) {
            player.addEffect(getPotionEffect(player));
        }
    }

    protected abstract MobEffectInstance getPotionEffect(ServerPlayer player);
}
