package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import net.minecraft.client.Minecraft;

public class AntiPortraitEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION =
            ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<AntiPortraitEffect> PROPERTIES =
            EffectProperties.Builder.of(AntiPortraitEffect.class)
                    .id("anti_portrait")
                    .supplier(AntiPortraitEffect::new)
//                    .conflict(BlackScreenEffect.class)
//                    .conflict(PortraitEffect.class)
                    .config(DURATION)
                    .build();

    public AntiPortraitEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGUI(IRenderGuiEvent event)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        var thirdWidth = width / 3;

        event.getGraphics().fill(thirdWidth, 0, width - thirdWidth, height, 0xFF000000);
    }
}
