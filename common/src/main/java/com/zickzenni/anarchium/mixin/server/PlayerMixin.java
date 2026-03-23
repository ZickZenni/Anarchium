package com.zickzenni.anarchium.mixin.server;

import com.zickzenni.anarchium.effect.impl.NoJumpingEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin
{
    @Inject(at = @At("HEAD"), method = "jumpFromGround()V", cancellable = true)
    public void isNoGravity(CallbackInfo ci)
    {
        if (NoJumpingEffect.ENABLED)
        {
            ci.cancel();
        }
    }
}
