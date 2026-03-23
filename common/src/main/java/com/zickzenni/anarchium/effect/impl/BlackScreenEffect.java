package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderGuiEvent;
import net.minecraft.client.Minecraft;

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

    public BlackScreenEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGui(IRenderGuiEvent event)
    {
        var minecraft = Minecraft.getInstance();
        minecraft.options.hideGui = true;

        var window = minecraft.getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        event.getGraphics().fill(0, 0, width, height, 0xFF000000);
    }

    @Override
    public void onEndClient()
    {
        Minecraft.getInstance().options.hideGui = false;
    }
}
