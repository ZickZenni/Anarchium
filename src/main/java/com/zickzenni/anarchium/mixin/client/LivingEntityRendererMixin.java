package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.impl.SkeletonsHaveSpinbotEffect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci)
    {
        var isPlayer = entity instanceof Player;

        if (EffectStates.enableWideLivingEntities && !isPlayer)
        {
            var rotation = 180.0F - entity.yBodyRot;
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            poseStack.scale(2, 1, 1);
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));
        }

        if (EffectStates.enableSpinningLivingEntities && !isPlayer)
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(EffectStates.spinningLivingEntityRotation));
        }

        if (SkeletonsHaveSpinbotEffect.enabled && entity instanceof AbstractSkeleton)
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(SkeletonsHaveSpinbotEffect.rotation));
        }
    }
}
