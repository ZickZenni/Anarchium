package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import net.minecraft.client.Minecraft;

public class PortraitEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<PortraitEffect> PROPERTIES =
            EffectProperties.Builder.of(PortraitEffect.class)
                    .id("portrait")
                    .supplier(PortraitEffect::new)
                    .conflict(BlackScreenEffect.class)
                    .conflict(AntiPortraitEffect.class)
                    .config(DURATION)
                    .build();

    public PortraitEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGui(IRenderGuiEvent event)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        var thirdWidth = width / 3;

        event.getGraphics().fill(0, 0, thirdWidth, height, 0xFF000000);
        event.getGraphics().fill(width - thirdWidth, 0, width, height, 0xFF000000);
    }
}
