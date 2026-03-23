package com.zickzenni.anarchium.event;

import net.minecraft.world.level.Level;

public interface ILevelTickEvent<T extends Level>
{
    /**
     * Retrieves the current level associated with this event.
     *
     * @return the level of type {@code T} associated with this event.
     */
    T getLevel();

    /**
     * Retrieves the current stage of the level tick event.
     *
     * @return the current stage of the level tick event, represented as a {@code Stage} enum value.
     */
    Stage getStage();

    /**
     * Represents the stages of the level tick event.
     */
    enum Stage
    {
        PRE,
        POST
    }
}
