package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Rotation;

public class GetRotatedEffect extends InstantEffect
{
    public static final EffectProperties<GetRotatedEffect> PROPERTIES =
            EffectProperties.Builder.of(GetRotatedEffect.class)
                    .id("get_rotated")
                    .supplier(GetRotatedEffect::new)
                    .build();

    public GetRotatedEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartClient()
    {
        var player = Minecraft.getInstance().player;

        assert player != null;

        player.setYRot(player.rotate(Rotation.CLOCKWISE_180));
        player.setDeltaMovement(player.getDeltaMovement().multiply(-1.0D, 1.0D, -1.0D));
    }
}
