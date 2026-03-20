package com.zickzenni.anarchium.mixin.client;

import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.MeshData;
import com.zickzenni.anarchium.effect.impl.MissingCSSAssets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferUploader.class)
public class BufferUploaderMixin
{
    @Inject(at = @At("HEAD"),
            method = "drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V")
    private static void test(MeshData meshData, CallbackInfo ci)
    {
        if (MissingCSSAssets.ENABLED && MissingCSSAssets.LEVEL_RENDERING)
        {
            MissingCSSAssets.replaceTexture();
        }
    }
}
