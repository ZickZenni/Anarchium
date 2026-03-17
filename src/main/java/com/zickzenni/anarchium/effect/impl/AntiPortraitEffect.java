package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class AntiPortraitEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);

    public static final EffectProperties<AntiPortraitEffect> PROPERTIES =
            EffectProperties.Builder.of(AntiPortraitEffect.class)
                    .id("anti_portrait")
                    .supplier(AntiPortraitEffect::new)
                    .conflict(BlackScreenEffect.class)
                    .conflict(PortraitEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public AntiPortraitEffect()
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

        graphics.fill(thirdWidth, 0, width - thirdWidth, height, 0xFF000000);
    }
}
