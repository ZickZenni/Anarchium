package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.GTA2Effect;
import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CameraType.class)
public class CameraTypeMixin
{
    @Inject(at = @At("HEAD"), method = "isFirstPerson()Z", cancellable = true)
    public void isFirstPerson(CallbackInfoReturnable<Boolean> cir)
    {
        if (GTA2Effect.ENABLED)
        {
            cir.setReturnValue(false);
        }
    }
}
