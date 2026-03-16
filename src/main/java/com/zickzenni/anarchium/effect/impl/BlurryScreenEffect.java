package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BlurryScreenEffect extends TimedEffect
{
    public static final EffectProperties<BlurryScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlurryScreenEffect.class)
                    .id("blurry_screen")
                    .supplier(BlurryScreenEffect::new)
                    .build();

    public static final int BLUR_RADIUS = 4;

    public static boolean ENABLED = false;

    public BlurryScreenEffect()
    {
        super(PROPERTIES.getId(), 20 * 33);
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

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var minecraft = Minecraft.getInstance();

        RenderSystem.disableDepthTest();
        minecraft.gameRenderer.processBlurEffect(deltaTime);
        minecraft.getMainRenderTarget().bindWrite(false);
    }
}
