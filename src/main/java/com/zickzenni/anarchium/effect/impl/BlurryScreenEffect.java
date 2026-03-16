package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BlurryScreenEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static ModConfigSpec.ConfigValue<Integer> BLUR_RADIUS;

    public static final EffectProperties<BlurryScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlurryScreenEffect.class)
                    .id("blurry_screen")
                    .supplier(BlurryScreenEffect::new)
                    .configure(BlurryScreenEffect::configure)
                    .build();

    // ======================================================

    public static boolean ENABLED = false;

    public BlurryScreenEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 33);
        BLUR_RADIUS = builder.define("blur_radius", 4);
    }
}
