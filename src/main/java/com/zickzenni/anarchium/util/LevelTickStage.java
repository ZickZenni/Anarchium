package com.zickzenni.anarchium.util;

/**
 * Represents the stage of the level tick event.
 *
 * @see net.neoforged.neoforge.event.tick.LevelTickEvent.Pre
 * @see net.neoforged.neoforge.event.tick.LevelTickEvent.Post
 */
public enum LevelTickStage
{
    /**
     * Fired once per game tick, per level, before the level performs work for the current tick.
     *
     * @see net.neoforged.neoforge.event.tick.LevelTickEvent.Pre
     */
    PRE,

    /**
     * Fired once per game tick, per level, after the level performs work for the current tick.
     *
     * @see net.neoforged.neoforge.event.tick.LevelTickEvent.Post
     */
    POST
}