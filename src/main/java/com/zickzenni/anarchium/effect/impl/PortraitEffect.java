package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.common.ModConfigSpec;

public class PortraitEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<PortraitEffect> PROPERTIES =
            EffectProperties.Builder.of(PortraitEffect.class)
                    .id("portrait")
                    .supplier(PortraitEffect::new)
                    .conflict(BlackScreenEffect.class)
                    .configure(PortraitEffect::configure)
                    .build();

    // ======================================================

    public PortraitEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        var thirdWidth = width / 3;

        graphics.fill(0, 0, thirdWidth, height, 0xFF000000);
        graphics.fill(width - thirdWidth, 0, width, height, 0xFF000000);
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 45);
    }
}
