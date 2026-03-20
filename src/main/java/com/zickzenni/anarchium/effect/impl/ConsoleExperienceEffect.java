package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class ConsoleExperienceEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final ConfigValue<Integer> FPS = ConfigValue.newInteger("fps", 30);
    public static final EffectProperties<ConsoleExperienceEffect> PROPERTIES =
            EffectProperties.Builder.of(ConsoleExperienceEffect.class)
                    .id("console_experience")
                    .supplier(ConsoleExperienceEffect::new)
                    .config(DURATION)
                    .config(FPS)
                    .build();

    public ConsoleExperienceEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onEndClient()
    {
        var minecraft = Minecraft.getInstance();
        minecraft.getWindow().setFramerateLimit(minecraft.options.framerateLimit().get());
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        Minecraft.getInstance().getWindow().setFramerateLimit(FPS.get());
    }
}
