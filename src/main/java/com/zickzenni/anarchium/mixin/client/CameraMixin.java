package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.RollingCameraEffect;
import com.zickzenni.anarchium.effect.impl.RotatingCameraEffect;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Shadow
    protected abstract void setRotation(float yRot, float xRot, float roll);

    @Shadow
    private float yRot;

    @Shadow
    private float xRot;

    @Shadow
    private float roll;

    @Inject(at = @At("TAIL"), method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V")
    public void setup(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci)
    {
        if (RotatingCameraEffect.ENABLED)
        {
            setRotation(yRot, xRot, RotatingCameraEffect.ROTATION);
        }

        if (RollingCameraEffect.ENABLED)
        {
            setRotation(yRot, RollingCameraEffect.ROTATION, roll);
        }
    }
}
