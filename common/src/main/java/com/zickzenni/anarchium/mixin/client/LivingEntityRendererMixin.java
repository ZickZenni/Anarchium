package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zickzenni.anarchium.effect.impl.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin
{
    @Shadow
    protected <T extends LivingEntity> RenderType getRenderType(T livingEntity,
                                                                boolean bodyVisible,
                                                                boolean translucent,
                                                                boolean glowing)
    {
        return null;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> void render(T entity,
                                                float entityYaw,
                                                float partialTicks,
                                                PoseStack stack,
                                                MultiBufferSource buffer,
                                                int packedLight,
                                                CallbackInfo ci)
    {
        var isPlayer = entity instanceof Player;

        if (UpsideDownMobs.ENABLED && !isPlayer)
        {
            UpsideDownMobs.modifyRotation$LivingEntityRenderer(entity, stack);
        }

        if (WideMobsEffect.ENABLED && !isPlayer)
        {
            WideMobsEffect.modifyWidth$LivingEntityRenderer(entity, stack);
        }

        if (SpinningMobsEffect.ENABLED && !isPlayer)
        {
            SpinningMobsEffect.modifyRotation$LivingEntityRenderer(stack);
        }

        if (SkeletonsHaveSpinbotEffect.ENABLED && entity instanceof AbstractSkeleton)
        {
            SkeletonsHaveSpinbotEffect.modifyRotation$LivingEntityRenderer(stack);
        }
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> void render$getOverlayCoords(T entity,
                                                                 float entityYaw,
                                                                 float partialTicks,
                                                                 PoseStack poseStack,
                                                                 MultiBufferSource buffer,
                                                                 int packedLight,
                                                                 CallbackInfo ci)
    {
        if (EveryoneIsAVillagerEffect.ENABLED)
        {
            EveryoneIsAVillagerEffect.replaceWithVillager$LivingEntityRenderer(
                    entity,
                    partialTicks,
                    poseStack,
                    buffer,
                    packedLight
            );
        }
    }

    // =================== EFFECTS ===================

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> RenderType render$getRenderType(@SuppressWarnings("rawtypes") LivingEntityRenderer ignoredInstance,
                                                                    T livingEntity,
                                                                    boolean bodyVisible,
                                                                    boolean translucent,
                                                                    boolean glowing)
    {
        if (EveryoneIsAVillagerEffect.ENABLED)
        {
            return null;
        }

        return getRenderType(livingEntity, bodyVisible, translucent, glowing);
    }
}
