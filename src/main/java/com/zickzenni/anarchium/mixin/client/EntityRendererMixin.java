package com.zickzenni.anarchium.mixin.client;

import com.zickzenni.anarchium.client.EffectStates;
import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin
{
    @ModifyArg(method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(D)Lnet/minecraft/world/phys/AABB;"), index = 0)
    public double modifyAABB(double value)
    {
        if (EffectStates.enableLargeEntities)
        {
            return value + 10.0;
        }

        return value;
    }
}
