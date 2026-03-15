package com.zickzenni.anarchium.mixin.server;

import com.zickzenni.anarchium.effect.impl.NoGravityEffect;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin
{
    @Inject(at = @At("HEAD"), method = "isNoGravity()Z", cancellable = true)
    public void isNoGravity(CallbackInfoReturnable<Boolean> ci)
    {
        if (NoGravityEffect.ENABLED)
        {
            ci.setReturnValue(true);
        }
    }
}
