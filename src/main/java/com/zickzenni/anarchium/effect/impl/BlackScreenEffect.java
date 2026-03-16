package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BlackScreenEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<BlackScreenEffect> PROPERTIES =
            EffectProperties.Builder.of(BlackScreenEffect.class)
                    .id("black_screen")
                    .supplier(BlackScreenEffect::new)
                    .conflict(PortraitEffect.class)
                    .configure(BlackScreenEffect::configure)
                    .build();

    // ======================================================

    public BlackScreenEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        graphics.fill(0, 0, width, height, 0xFF000000);
    }

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 33);
    }
}
