package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.client.EffectStates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin
{
    @Unique
    private static final RandomSource anarchium$random = RandomSource.create();

    @ModifyVariable(
            method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V",
            at = @At(value = "STORE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;getSound()Lnet/minecraft/client/resources/sounds/Sound;"),
            ordinal = 0
    )
    public Sound getSound(Sound original)
    {
        if (EffectStates.enableReplaceEverySoundWithVillagers)
        {
            var soundEvents = Minecraft.getInstance().getSoundManager().getSoundEvent(SoundEvents.VILLAGER_AMBIENT.getLocation());
            return soundEvents != null ? soundEvents.getSound(anarchium$random) : SoundManager.EMPTY_SOUND;
        }

        return original;
    }

    @Inject(at = @At("HEAD"), method = "calculatePitch(Lnet/minecraft/client/resources/sounds/SoundInstance;)F", cancellable = true)
    private void calculatePitch(SoundInstance instance, CallbackInfoReturnable<Float> cir)
    {
        if (EffectStates.enableCustomPitch)
        {
            cir.setReturnValue(EffectStates.customPitch);
        }
    }
}
