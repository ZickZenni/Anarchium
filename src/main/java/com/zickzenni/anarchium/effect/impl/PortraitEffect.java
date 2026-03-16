package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class PortraitEffect extends TimedEffect
{
    public static final EffectProperties<PortraitEffect> PROPERTIES =
            EffectProperties.Builder.of(PortraitEffect.class)
                    .id("portrait")
                    .supplier(PortraitEffect::new)
                    .conflict(BlackScreenEffect.class)
                    .build();

    public PortraitEffect()
    {
        super(PROPERTIES.getId(), 20 * 45);
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
}
