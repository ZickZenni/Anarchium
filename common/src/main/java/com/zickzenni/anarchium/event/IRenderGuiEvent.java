package com.zickzenni.anarchium.event;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface IRenderGuiEvent
{
    /**
     * Retrieves the graphical context associated with the GUI rendering event.
     *
     * @return the {@code GuiGraphics} instance used for rendering the GUI.
     */
    GuiGraphics getGraphics();

    /**
     * Retrieves the partial tick value associated with the rendering event.
     *
     * @return the {@code DeltaTracker} instance representing the partial tick.
     */
    DeltaTracker getPartialTick();

    /**
     * Retrieves the current stage of the GUI rendering event.
     *
     * @return the current stage of the GUI rendering event, represented as a {@code Stage} enum value.
     */
    Stage getStage();

    /**
     * Represents the stages of the GUI rendering event.
     */
    enum Stage
    {
        PRE,
        POST
    }
}
