package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;

public class EveryoneIsAVillagerEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 50);
    public static final EffectProperties<EveryoneIsAVillagerEffect> PROPERTIES =
            EffectProperties.Builder.of(EveryoneIsAVillagerEffect.class)
                    .id("everyone_is_a_villager")
                    .supplier(EveryoneIsAVillagerEffect::new)
                    .config(DURATION)
                    .build();

    private static final ResourceLocation VILLAGER_BASE_SKIN =
            ResourceLocation.withDefaultNamespace("textures/entity/villager/villager.png");
    private static final ResourceLocation VILLAGER_PLAINS_CLOTH =
            getResourceLocation(BuiltInRegistries.VILLAGER_TYPE.getKey(VillagerType.PLAINS));

    public static VillagerModel<Villager> VILLAGER_MODEL;
    public static boolean ENABLED = false;

    public EveryoneIsAVillagerEffect()
    {
        super(PROPERTIES.getId(), 20 * 50);
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
    }

    public static <T extends LivingEntity> void replaceWithVillager$LivingEntityRenderer(T entity,
                                                                                         float partialTicks,
                                                                                         PoseStack stack,
                                                                                         MultiBufferSource buffer,
                                                                                         int packedLight)
    {
        var model = EveryoneIsAVillagerEffect.VILLAGER_MODEL;
        var shouldSit =
                entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle() instanceof LivingEntity);

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
        model.renderToBuffer(stack, main, packedLight, overlayCoords, -1);

        var cloth = buffer.getBuffer(RenderType.entityCutoutNoCull(VILLAGER_PLAINS_CLOTH));
        model.renderToBuffer(stack, cloth, packedLight, overlayCoords, -1);

        stack.scale(0, 0, 0);
    }

    private static ResourceLocation getResourceLocation(ResourceLocation location)
    {
        return location.withPath(path -> "textures/entity/villager/type/" + path + ".png");
    }
}
