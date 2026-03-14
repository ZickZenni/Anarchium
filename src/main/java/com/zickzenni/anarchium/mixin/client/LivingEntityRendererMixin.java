package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.impl.EveryoneIsAVillagerEffect;
import com.zickzenni.anarchium.effect.impl.SkeletonsHaveSpinbotEffect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin
{
    @Unique
    private static final ResourceLocation VILLAGER_BASE_SKIN = ResourceLocation.withDefaultNamespace("textures/entity/villager/villager.png");

    @Unique
    private static final ResourceLocation VILLAGER_PLAINS_CLOTH = anarchium$getResourceLocation(BuiltInRegistries.VILLAGER_TYPE.getKey(VillagerType.PLAINS));

    @Shadow
    protected <T extends LivingEntity> RenderType getRenderType(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing)
    {
        return null;
    }

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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> void render$getOverlayCoords(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci)
    {
        if (EveryoneIsAVillagerEffect.ENABLED)
        {
            anarchium$replaceWithVillager(entity, partialTicks, poseStack, buffer, packedLight);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"),
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public <T extends LivingEntity> RenderType render$getRenderType(@SuppressWarnings("rawtypes") LivingEntityRenderer ignoredInstance, T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing)
    {
        if (EveryoneIsAVillagerEffect.ENABLED)
        {
            return null;
        }

        return getRenderType(livingEntity, bodyVisible, translucent, glowing);
    }

    @Unique
    private static ResourceLocation anarchium$getResourceLocation(ResourceLocation location)
    {
        return location.withPath(p_247944_ -> "textures/entity/villager/type/" + p_247944_ + ".png");
    }

    // =================== EFFECTS ===================

    @Unique
    private static <T extends LivingEntity> void anarchium$replaceWithVillager(T entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        var model = EveryoneIsAVillagerEffect.VILLAGER_MODEL;
        var shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());

        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float f2 = f1 - f;
        if (shouldSit && entity.getVehicle() instanceof LivingEntity livingentity)
        {
            f = Mth.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f7 = Mth.wrapDegrees(f2);
            if (f7 < -85.0F)
            {
                f7 = -85.0F;
            }

            if (f7 >= 85.0F)
            {
                f7 = 85.0F;
            }

            f = f1 - f7;
            if (f7 * f7 > 2500.0F)
            {
                f += f7 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        if (LivingEntityRenderer.isEntityUpsideDown(entity))
        {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        f2 = Mth.wrapDegrees(f2);

        model.attackTime = entity.getAttackAnim(partialTicks);
        model.riding = shouldSit;
        model.young = entity.isBaby();

        float f4 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entity.isAlive())
        {
            f4 = entity.walkAnimation.speed(partialTicks);
            f5 = entity.walkAnimation.position(partialTicks);
            if (entity.isBaby())
            {
                f5 *= 3.0F;
            }

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }
        }

        //noinspection DataFlowIssue
        model.setupAnim(null, f5, f4, (float) entity.tickCount + partialTicks, f2, f6);

        int overlayCoords = LivingEntityRenderer.getOverlayCoords(entity, 0.0f);

        var main = buffer.getBuffer(RenderType.entityCutoutNoCull(VILLAGER_BASE_SKIN));
        model.renderToBuffer(poseStack, main, packedLight, overlayCoords, -1);

        var cloth = buffer.getBuffer(RenderType.entityCutoutNoCull(VILLAGER_PLAINS_CLOTH));
        model.renderToBuffer(poseStack, cloth, packedLight, overlayCoords, -1);

        poseStack.scale(0, 0, 0);
    }
}
