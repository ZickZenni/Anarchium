package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import net.minecraft.client.Minecraft;

public class BlurryScreenEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 33);
    public static final ConfigValue<Integer> BLUR_RADIUS = ConfigValue.newInteger("blur_radius", 4);
    public static final EffectProperties<BlurryScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlurryScreenEffect.class)
                    .id("blurry_screen")
                    .supplier(BlurryScreenEffect::new)
                    .config(DURATION)
                    .config(BLUR_RADIUS)
                    .build();

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
    public void onRenderGui(IRenderGuiEvent event)
    {
        var minecraft = Minecraft.getInstance();

        RenderSystem.disableDepthTest();
        minecraft.gameRenderer.processBlurEffect(event.getPartialTick().getRealtimeDeltaTicks());
        minecraft.getMainRenderTarget().bindWrite(false);
    }
}
