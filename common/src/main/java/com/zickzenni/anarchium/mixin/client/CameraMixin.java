package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.GTA2Effect;
import com.zickzenni.anarchium.effect.impl.GoodbyeEffect;
import com.zickzenni.anarchium.effect.impl.RollingCameraEffect;
import com.zickzenni.anarchium.effect.impl.RotatingCameraEffect;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Shadow
    private static final Vector3f FORWARDS = new Vector3f(0.0F, 0.0F, -1.0F);

    @Shadow
    private static final Vector3f UP = new Vector3f(0.0F, 1.0F, 0.0F);

    @Shadow
    private static final Vector3f LEFT = new Vector3f(-1.0F, 0.0F, 0.0F);

    @Shadow
    protected abstract void setPosition(double x, double y, double z);

    @Shadow
    protected abstract void setRotation(float yRot, float xRot);

    @Final
    @Shadow
    private Vector3f forwards;

    @Final
    @Shadow
    private Vector3f up;

    @Final
    @Shadow
    private Vector3f left;

    @Shadow
    private float yRot;

    @Shadow
    private float xRot;

    @Final
    @Shadow
    private Quaternionf rotation;

    @Inject(at = @At("HEAD"),
            method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
            cancellable = true)
    public void setup$head(BlockGetter level,
                           Entity entity,
                           boolean detached,
                           boolean thirdPersonReverse,
                           float partialTick,
                           CallbackInfo ci)
    {
        if (GoodbyeEffect.ENABLED)
        {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"),
            method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V")
    public void setup$tail(BlockGetter level,
                           Entity entity,
                           boolean detached,
                           boolean thirdPersonReverse,
                           float partialTick,
                           CallbackInfo ci)
    {
        if (GTA2Effect.ENABLED)
        {
            GTA2Effect.updateCamera$Camera(entity, partialTick, this::setPosition, this::setRotation);
        }

        if (RollingCameraEffect.ENABLED)
        {
            setRotation(yRot, RollingCameraEffect.ROTATION);
        }

        if (RotatingCameraEffect.ENABLED)
        {
            this.rotation.rotationYXZ(
                    (float) Math.PI - yRot * ((float) Math.PI / 180F),
                    -xRot * ((float) Math.PI / 180F),
                    -RotatingCameraEffect.ROTATION * (float) (Math.PI / 180.0)
            );
            FORWARDS.rotate(this.rotation, this.forwards);
            UP.rotate(this.rotation, this.up);
            LEFT.rotate(this.rotation, this.left);
        }
    }
}
