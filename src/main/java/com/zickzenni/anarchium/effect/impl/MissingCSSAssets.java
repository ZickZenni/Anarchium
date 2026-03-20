package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.resources.ResourceLocation;

public class MissingCSSAssets extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<MissingCSSAssets> PROPERTIES =
            EffectProperties.Builder.of(MissingCSSAssets.class)
                    .id("missing_css_assets")
                    .supplier(MissingCSSAssets::new)
                    .config(DURATION)
                    .build();

    private static final ResourceLocation MISSINGNO = ResourceLocation.withDefaultNamespace("missingno");

    public static boolean ENABLED = false;
    public static boolean LEVEL_RENDERING = false;

    public MissingCSSAssets()
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

    public static void replaceTexture()
    {
        RenderSystem.setShaderTexture(0, MISSINGNO);
    }
}
