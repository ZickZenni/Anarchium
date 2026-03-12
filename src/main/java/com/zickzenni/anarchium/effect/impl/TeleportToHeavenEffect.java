package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class TeleportToHeavenEffect extends BaseTeleportToEffect
{
    public static final EffectSupplier<TeleportToHeavenEffect> SUPPLIER = TeleportToHeavenEffect::new;

    public static final ResourceLocation ID = Anarchium.location("teleport_to_heaven");

    public static final double HEIGHT = 850;

    public TeleportToHeavenEffect()
    {
        super(ID);
    }

    @Override
    protected Vec3 getTeleportPosition(ServerPlayer player)
    {
        var position = player.position();
        return new Vec3(position.x, HEIGHT, position.z);
    }
}
