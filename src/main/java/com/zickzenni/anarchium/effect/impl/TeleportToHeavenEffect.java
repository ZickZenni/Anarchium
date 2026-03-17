package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.base.BaseTeleportToEffect;
import com.zickzenni.anarchium.effect.EffectProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class TeleportToHeavenEffect extends BaseTeleportToEffect
{
    public static final EffectProperties<TeleportToHeavenEffect> PROPERTIES =
            EffectProperties.Builder.of(TeleportToHeavenEffect.class)
                    .id("teleport_to_heaven")
                    .supplier(TeleportToHeavenEffect::new)
                    .build();

    public static final double HEIGHT = 850;

    public TeleportToHeavenEffect()
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
