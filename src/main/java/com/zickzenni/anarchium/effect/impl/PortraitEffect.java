package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class PortraitEffect extends TimedEffect
{
    public static final EffectSupplier<PortraitEffect> SUPPLIER = PortraitEffect::new;

    public static final ResourceLocation ID = Anarchium.location("portrait");

    public PortraitEffect()
    {
        super(ID, 20 * 45);
    }

    @Override
    public void onRenderGUI(GuiGraphics graphics)
    {
        var window = Minecraft.getInstance().getWindow();
        var width = window.getGuiScaledWidth();
        var height = window.getGuiScaledHeight();

        var thirdWidth = width / 3;

        graphics.fill(0, 0, thirdWidth, height, 0xFF000000);
        graphics.fill(width - thirdWidth, 0, width, height, 0xFF000000);
    }
}
