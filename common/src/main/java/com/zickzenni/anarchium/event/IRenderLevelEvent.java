package com.zickzenni.anarchium.event;

import net.minecraft.client.DeltaTracker;

public interface IRenderLevelEvent
{
    /**
     * Retrieves the stage of the rendering level event.
     *
     * @return the current stage of the rendering level event, represented as a {@code Stage} enum value.
     */
    Stage getStage();

    /**
     * Retrieves the partial tick.
     *
     * @return the partial tick.
     */
    DeltaTracker getPartialTick();

    /**
     * Represents the stages of the rendering level event.
     */
    enum Stage
    {
        START,
        AFTER_ENTITIES,
        AFTER_TRANSLUCENT,
        LAST,
        UNKNOWN
    }
}
