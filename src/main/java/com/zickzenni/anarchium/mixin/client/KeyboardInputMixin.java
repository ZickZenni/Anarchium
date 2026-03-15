package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.ForceSneakEffect;
import com.zickzenni.anarchium.effect.impl.NoSneakingEffect;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input
{
    @Inject(at = @At("TAIL"), method = "tick(ZF)V")
    public void tick(boolean isSneaking, float sneakingSpeedMultiplier, CallbackInfo ci)
    {
        if (NoSneakingEffect.ENABLED)
        {
            this.shiftKeyDown = false;

            if (isSneaking) {
                this.leftImpulse /= sneakingSpeedMultiplier;
                this.forwardImpulse /= sneakingSpeedMultiplier;
            }

            return;
        }

        if (ForceSneakEffect.ENABLED)
        {
            this.shiftKeyDown = true;

            if (!isSneaking) {
                this.leftImpulse *= sneakingSpeedMultiplier;
                this.forwardImpulse *= sneakingSpeedMultiplier;
            }
        }
    }
}
