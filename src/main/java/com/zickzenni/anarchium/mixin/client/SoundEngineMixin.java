package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.impl.ReplaceEverySoundWithVillagersEffect;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin
{
    @ModifyVariable(
            method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V",
            at = @At(value = "STORE",
                    target = "Lnet/minecraft/client/resources/sounds/SoundInstance;getSound()Lnet/minecraft/client/resources/sounds/Sound;"),
            ordinal = 0
    )
    public Sound getSound(Sound original)
    {
        if (ReplaceEverySoundWithVillagersEffect.ENABLED)
        {
            return ReplaceEverySoundWithVillagersEffect.replaceSound$SoundEngine();
        }

        return original;
    }

    @Inject(at = @At("HEAD"),
            method = "calculatePitch(Lnet/minecraft/client/resources/sounds/SoundInstance;)F",
            cancellable = true)
    private void calculatePitch(SoundInstance instance, CallbackInfoReturnable<Float> cir)
    {
        if (EffectStates.enableCustomPitch)
        {
            cir.setReturnValue(EffectStates.customPitch);
        }
    }
}
