package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class PortraitEffect extends TimedEffect
{
    public static final EffectSupplier<PortraitEffect> SUPPLIER = PortraitEffect::new;

    public static final ResourceLocation ID = Anarchium.location("portrait");

    public PortraitEffect()
    {
        super(ID, 20 * 45);
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics, float deltaTime)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        var thirdWidth = width / 3;

        graphics.fill(0, 0, thirdWidth, height, 0xFF000000);
        graphics.fill(width - thirdWidth, 0, width, height, 0xFF000000);
    }
}
