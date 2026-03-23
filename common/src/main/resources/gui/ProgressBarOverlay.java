package com.zickzenni.anarchium.client.gui;

import com.zickzenni.anarchium.Anarchium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class ProgressBarOverlay implements Overlay
{
    private int windowWidth;
    private float progress;

    public ProgressBarOverlay()
    {
        this.windowWidth = 0;
        this.progress = 0;
    }

    @Override
    public void render(GuiGraphics graphics, float deltaTime)
    {
        final var instance = Anarchium.getClient();
        final var minecraft = Minecraft.getInstance();

        final var ticks = instance.getTimer().getTicks();
        final var duration = instance.getTimer().getDuration();

        final var windowWidth = minecraft.getWindow().getGuiScaledWidth();
        final var progress = (float) (duration - ticks) / (float) duration;

        if (this.windowWidth != windowWidth)
        {
            this.windowWidth = windowWidth;
            this.progress = progress;
        } else
        {
            this.progress = Mth.lerp(deltaTime, this.progress, progress);
        }

        if (Float.isNaN(this.progress))
        {
            this.progress = 0;
        }

        graphics.fill(0, 0, this.windowWidth, 10, 0x80000000);
        graphics.fill(0, 0, (int) Math.floor(this.progress * this.windowWidth), 10, 0xFF1144CC);
    }
}
