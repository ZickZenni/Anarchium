package com.zickzenni.anarchium.effect.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.neoforged.neoforge.client.IRenderableSection;
import org.joml.Matrix4f;

public record EffectRenderLevelStageEvent(Stage stage, LevelRenderer levelRenderer, LevelRenderState levelRenderState,
                                          PoseStack poseStack, Matrix4f modelViewMatrix,
                                          Iterable<? extends IRenderableSection> renderableSections)
{
    public enum Stage
    {
        AFTER_SKY,
        AFTER_OPAQUE_BLOCKS,
        AFTER_ENTITIES,
        AFTER_TRANSLUCENT_BLOCKS,
        AFTER_TRIPWIRE_BLOCKS,
        AFTER_PARTICLES,
        AFTER_WEATHER,
        AFTER_LEVEL
    }

}
