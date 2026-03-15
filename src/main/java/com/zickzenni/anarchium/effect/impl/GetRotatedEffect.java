package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;

public class GetRotatedEffect extends InstantEffect
{
    public static final EffectSupplier<GetRotatedEffect> SUPPLIER = GetRotatedEffect::new;

    public static final ResourceLocation ID = Anarchium.location("get_rotated");

    public GetRotatedEffect()
    {
        super(ID);
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
