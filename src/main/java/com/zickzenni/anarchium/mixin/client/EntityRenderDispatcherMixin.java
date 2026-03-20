package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zickzenni.anarchium.effect.impl.LargeEntitiesEffect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin
{
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
            ),
            method = "render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
    )
    public <E extends Entity> void render(E entity,
                                          double x,
                                          double y,
                                          double z,
                                          float rotationYaw,
                                          float partialTicks,
                                          PoseStack poseStack,
                                          MultiBufferSource buffer,
                                          int packedLight,
                                          CallbackInfo ci)
    {
        if (LargeEntitiesEffect.ENABLED)
        {
            var scale = LargeEntitiesEffect.SCALE_MULTIPLIER.get().floatValue();
            poseStack.scale(scale, scale, scale);
        }
    }
}
