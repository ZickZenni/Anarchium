package com.zickzenni.anarchium.client.gui;

import com.zickzenni.anarchium.client.AnarchiumClient;
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
        final var instance = AnarchiumClient.getInstance();
        final var minecraft = Minecraft.getInstance();

        final var nextProgress = (float) (instance.timerDuration - instance.timerTicks) / (float) instance.timerDuration;

        if (windowWidth != minecraft.getWindow().getGuiScaledWidth())
        {
            windowWidth = minecraft.getWindow().getGuiScaledWidth();
            progress = nextProgress;
        } else
        {
            progress = Mth.lerp(deltaTime * 2.0f, progress, nextProgress);
        }

        if (Float.isNaN(progress))
        {
            progress = 0;
        }

        graphics.fill(
                0,
                0,
                windowWidth,
                10,
                0x80000000
        );

        graphics.fill(
                0,
                0,
                (int) Math.floor(progress * windowWidth),
                10,
                0xFF1144CC
        );
    }
}
