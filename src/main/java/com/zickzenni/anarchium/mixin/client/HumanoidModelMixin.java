package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.effect.impl.SkeletonsHaveSpinbotEffect;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin
{
    @Shadow
    @Final
    public ModelPart head;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public <T extends LivingEntity> void setupAnim(T entity, float ignoredA, float ignoredB, float ignoredC,
                                                   float ignoredD, float ignoredE, CallbackInfo ci)
    {
        if (SkeletonsHaveSpinbotEffect.enabled && entity instanceof AbstractSkeleton)
        {
            head.yRot = -(SkeletonsHaveSpinbotEffect.rotation * 1.2f) * ((float) Math.PI / 180F);
            head.xRot = -80 * (float) Math.PI / 180;
        }
    }
}
