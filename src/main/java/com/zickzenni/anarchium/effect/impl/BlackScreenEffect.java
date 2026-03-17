package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BlackScreenEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 33);

    public static final EffectProperties<BlackScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlackScreenEffect.class)
                    .id("black_screen")
                    .supplier(BlackScreenEffect::new)
                    .conflict(PortraitEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public BlackScreenEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var minecraft = Minecraft.getInstance();
        minecraft.options.hideGui = true;

        var window = minecraft.getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        graphics.fill(0, 0, width, height, 0xFF000000);
    }

    @Override
    public void onEndClient()
    {
        Minecraft.getInstance().options.hideGui = false;
    }
}
