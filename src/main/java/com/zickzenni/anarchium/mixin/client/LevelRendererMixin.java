package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.zickzenni.anarchium.effect.impl.WhereAreMyChunksEffect;
import com.zickzenni.anarchium.effect.impl.WhereIsTheSkyEffect;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Inject(at = @At("HEAD"), method = "renderSectionLayer(Lnet/minecraft/client/renderer/RenderType;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", cancellable = true)
    private void renderSectionLayer(RenderType renderType, double x, double y, double z, Matrix4f frustrumMatrix, Matrix4f projectionMatrix, CallbackInfo ci)
    {
        if (WhereAreMyChunksEffect.ENABLED)
        {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
    public void renderSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci)
    {
        if (WhereIsTheSkyEffect.ENABLED)
        {
            anarchium$drawColoredSky(frustumMatrix, -16777216);
            ci.cancel();
        }
    }

    /**
     * Draws a sky using one color.
     *
     * @param frustumMatrix - The frustum matrix.
     * @param color         - The color of the sky.
     * @see net.minecraft.client.renderer.LevelRenderer#renderEndSky
     */
    @Unique
    private static void anarchium$drawColoredSky(Matrix4f frustumMatrix, int color)
    {
        PoseStack poseStack = new PoseStack();
        poseStack.mulPose(frustumMatrix);

        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tesselator = Tesselator.getInstance();

        for (int i = 0; i < 6; i++)
        {
            poseStack.pushPose();
            if (i == 1)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4)
            {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5)
            {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setColor(color);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setColor(color);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setColor(color);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setColor(color);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
}
