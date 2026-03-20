package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.InvertedMouseEffect;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin
{
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"),
            method = "turnPlayer(D)V")
    public void turn(LocalPlayer instance, double yRot, double xRot)
    {
        if (InvertedMouseEffect.ENABLED)
        {
            instance.turn(-yRot, -xRot);
        }
    }
}
