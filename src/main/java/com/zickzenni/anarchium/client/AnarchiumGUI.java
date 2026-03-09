package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.client.effect.ICustomEffectName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class AnarchiumGUI
{
    private static final int HISTORY_ITEM_X_OFFSET = 24;

    private static final int HISTORY_ITEM_Y_OFFSET = 24;

    private static final int HISTORY_ITEM_WIDTH = 32;

    private static final int HISTORY_ITEM_HEIGHT = 10;

    private static final int BACKGROUND_COLOR = 0x80000000;

    private AnarchiumGUI() {}

    /**
     * Renders the GUI.
     */
    public static void render(GuiGraphics graphics)
    {
        /*
         * Only render stuff when we are in the world.
         */
        if (Minecraft.getInstance().player == null)
        {
            return;
        }

        renderBar(graphics);
        renderHistory(graphics);
    }

    /**
     * Renders the timer bar.
     */
    private static void renderBar(GuiGraphics graphics)
    {
        final var instance = AnarchiumClient.getInstance();
        final var minecraft = Minecraft.getInstance();

        final var progress = (float) instance.timerTicks / (float) instance.timerDuration;
        final var fillWidth = (int) Math.floor(progress * minecraft.getWindow().getGuiScaledWidth());
        final var windowWidth = minecraft.getWindow().getGuiScaledWidth();

        graphics.fill(
                0,
                0,
                windowWidth,
                10,
                BACKGROUND_COLOR
        );

        graphics.fill(
                0,
                0,
                fillWidth,
                10,
                0xFF1144CC
        );
    }

    /**
     * Renders the history of effects that are running or were active.
     */
    private static void renderHistory(GuiGraphics graphics)
    {
        final var instance = AnarchiumClient.getInstance();
        final var minecraft = Minecraft.getInstance();

        final var windowWidth = minecraft.getWindow().getGuiScaledWidth();

        int y = 0;

        for (var effect : instance.effectManager.effects)
        {
            var name = effect.identifier.toString();

            if (effect.handler instanceof ICustomEffectName)
            {
                name = ((ICustomEffectName) effect.handler).getCustomName();
            }

            final var textWidth = minecraft.font.width(name);

            final var progress = (float) effect.ticks / (float) effect.properties.getDurationTicks();
            final var fillWidth = (int) Math.floor(progress * HISTORY_ITEM_WIDTH);

            /*
             * Background
             */
            graphics.fill(
                    windowWidth - HISTORY_ITEM_X_OFFSET - HISTORY_ITEM_WIDTH,
                    HISTORY_ITEM_Y_OFFSET + y,
                    windowWidth - HISTORY_ITEM_X_OFFSET,
                    HISTORY_ITEM_Y_OFFSET + HISTORY_ITEM_HEIGHT + y,
                    BACKGROUND_COLOR
            );

            /*
             * Time "Bar"
             */
            graphics.fill(
                    windowWidth - HISTORY_ITEM_X_OFFSET - HISTORY_ITEM_WIDTH,
                    HISTORY_ITEM_Y_OFFSET + y,
                    windowWidth - HISTORY_ITEM_X_OFFSET - fillWidth,
                    HISTORY_ITEM_Y_OFFSET + HISTORY_ITEM_HEIGHT + y,
                    0xFFFFFFFF
            );

            graphics.drawString(
                    minecraft.font,
                    name,
                    windowWidth - HISTORY_ITEM_X_OFFSET - textWidth - HISTORY_ITEM_WIDTH - 10,
                    HISTORY_ITEM_Y_OFFSET + y,
                    0xFFFFFFFF
            );

            y += HISTORY_ITEM_HEIGHT + 12;
        }
    }
}
