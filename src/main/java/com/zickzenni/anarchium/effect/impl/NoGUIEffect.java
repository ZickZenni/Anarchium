package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class NoGUIEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 60);
    public static final EffectProperties<NoGUIEffect> PROPERTIES =
            EffectProperties.Builder.of(NoGUIEffect.class)
                    .id("no_gui")
                    .supplier(NoGUIEffect::new)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public NoGUIEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onEndClient()
    {
        Minecraft.getInstance().options.hideGui = false;
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        Minecraft.getInstance().options.hideGui = true;
    }
}
