package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.BlurryScreenEffect;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Options.class)
public abstract class OptionsMixin
{
    @Inject(at = @At("RETURN"), method = "getMenuBackgroundBlurriness()I", cancellable = true)
    public void getMenuBackgroundBlurriness(CallbackInfoReturnable<Integer> cir)
    {
        if (BlurryScreenEffect.ENABLED)
        {
            cir.setReturnValue(BlurryScreenEffect.BLUR_RADIUS);
        }
    }
}
