package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zickzenni.anarchium.effect.impl.BiggerBlockEntitiesEffect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin
{
    @Inject(at = @At("HEAD"), method = "setupAndRender(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V")
    private static <T extends BlockEntity> void setupAndRender$head(BlockEntityRenderer<T> renderer, T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci)
    {
        if (BiggerBlockEntitiesEffect.ENABLED)
        {
            float scale = BiggerBlockEntitiesEffect.SCALE_MULTIPLIER;
            float offset = -((scale - 1) / 2);

            poseStack.pushPose();
            poseStack.translate(offset, 0, offset);
            poseStack.scale(scale, scale, scale);
        }
    }

    @Inject(at = @At("TAIL"), method = "setupAndRender(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V")
    private static <T extends BlockEntity> void setupAndRender$tail(BlockEntityRenderer<T> renderer, T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci)
    {
        if (BiggerBlockEntitiesEffect.ENABLED)
        {
            poseStack.popPose();
        }
    }
}
