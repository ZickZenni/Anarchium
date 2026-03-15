package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class BlackScreenEffect extends TimedEffect
{
    public static final EffectSupplier<BlackScreenEffect> SUPPLIER = BlackScreenEffect::new;

    public static final ResourceLocation ID = Anarchium.location("black_screen");

    public BlackScreenEffect()
    {
        super(ID, 20 * 33);
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
