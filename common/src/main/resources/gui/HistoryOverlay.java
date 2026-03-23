package com.zickzenni.anarchium.client.gui;

import com.zickzenni.anarchium.client.ClientEffectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HistoryOverlay implements Overlay
{
    private static final int ITEM_X_OFFSET = 24;
    private static final int ITEM_Y_OFFSET = 24;
    private static final int ITEM_WIDTH = 32;
    private static final int ITEM_HEIGHT = 8;

    private int y;

    public HistoryOverlay()
    {
        this.y = 0;
    }

    @Override
    public void render(GuiGraphics graphics, float deltaTime)
    {
        this.y = 0;

        for (var name : ClientEffectManager.getHistory())
        {
            renderHistoryItem(graphics, name, 0, 0);
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            renderHistoryItem(graphics, effect.getGUIName(), effect.getTicks(), effect.getDurationTicks());
        }
    }

    /**
     * Renders a single history item.
     * Can also be an active effect.
     */
    private void renderHistoryItem(GuiGraphics graphics, String name, int ticks, int durationTicks)
    {
        final var minecraft = Minecraft.getInstance();
        final var textWidth = minecraft.font.width(name);
        final var windowWidth = minecraft.getWindow().getGuiScaledWidth();

        int textXOffset = 0;

        if (durationTicks > 0)
        {
            final var progress = (float) ticks / (float) durationTicks;
            final var fillWidth = (int) Math.floor(progress * ITEM_WIDTH);

            /*
             * Background
             */
            graphics.fill(windowWidth - ITEM_X_OFFSET - ITEM_WIDTH, ITEM_Y_OFFSET + y, windowWidth - ITEM_X_OFFSET, ITEM_Y_OFFSET + ITEM_HEIGHT + y, 0x80000000);

            /*
             * Time "Bar"
             */
            graphics.fill(windowWidth - ITEM_X_OFFSET - ITEM_WIDTH, ITEM_Y_OFFSET + y, windowWidth - ITEM_X_OFFSET - fillWidth, ITEM_Y_OFFSET + ITEM_HEIGHT + y, 0xFFFFFFFF);

            textXOffset += ITEM_WIDTH + 10;
        }

        graphics.drawString(minecraft.font, name, windowWidth - ITEM_X_OFFSET - textWidth - textXOffset, ITEM_Y_OFFSET + y, 0xFFFFFFFF);

        y += ITEM_HEIGHT + 12;
    }
}
