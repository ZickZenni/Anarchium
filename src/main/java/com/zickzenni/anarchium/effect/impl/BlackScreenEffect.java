package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BlackScreenEffect extends TimedEffect
{
    public static final EffectProperties<BlackScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlackScreenEffect.class)
                    .id("black_screen")
                    .supplier(BlackScreenEffect::new)
                    .conflict(PortraitEffect.class)
                    .build();

    public BlackScreenEffect()
    {
        super(PROPERTIES.getId(), 20 * 33);
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        graphics.fill(0, 0, width, height, 0xFF000000);
    }
}
