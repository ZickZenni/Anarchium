package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class BlurryScreenEffect extends TimedEffect
{
    public static final EffectSupplier<BlurryScreenEffect> SUPPLIER = BlurryScreenEffect::new;

    public static final ResourceLocation ID = Anarchium.location("blurry_screen");

    public static final int BLUR_RADIUS = 4;

    public static boolean ENABLED = false;

    public BlurryScreenEffect()
    {
        super(ID, 20 * 33);
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
