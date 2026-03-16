package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class TeleportToVoidEffect extends BaseTeleportToEffect
{
    public static final EffectProperties<TeleportToVoidEffect> PROPERTIES =
            EffectProperties.Builder.of(TeleportToVoidEffect.class)
                    .id("teleport_to_void")
                    .supplier(TeleportToVoidEffect::new)
                    .build();

    public static final double HEIGHT = -67;

    public TeleportToVoidEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    protected Vec3 getTeleportPosition(ServerPlayer player)
    {
        var position = player.position();
        return new Vec3(position.x, HEIGHT, position.z);
    }
}
