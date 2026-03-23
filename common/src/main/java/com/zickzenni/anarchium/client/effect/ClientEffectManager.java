package com.zickzenni.anarchium.client.effect;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.effect.event.ILevelTickEvent;
import com.zickzenni.anarchium.effect.event.IRenderGuiEvent;
import com.zickzenni.anarchium.effect.event.IRenderLevelEvent;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientEffectManager
{
    private static final int MAX_HISTORY_SIZE = 5;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<Effect> EFFECTS = new ArrayList<>();
    private static final List<String> HISTORY = new ArrayList<>();

    private ClientEffectManager()
    {
    }

    public static void tick(ILevelTickEvent<ClientLevel> event)
    {
        for (var effects : EFFECTS)
        {
            effects.onLevelTickClient(event);
        }
    }

    public static void sendRenderLevelStageEvent(IRenderLevelEvent event)
    {
        var deltaTime = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks();

        for (var effects : EFFECTS)
        {
            effects.onRenderLevel(event);
        }
    }

    /**
     * Sends a "render gui" event to all effects.
     *
     * @see net.neoforged.neoforge.client.event.RenderGuiEvent
     */
    public static void sendRenderGuiEvent(IRenderGuiEvent event)
    {
        for (var effects : EFFECTS)
        {
            effects.onRenderGUI(event);
        }
    }

    /**
     * Creates a new effect.
     */
    public static @Nullable Effect createEffect(ResourceLocation location)
    {
        var properties = EffectRegistry.getRegistry().get(location);

        if (properties == null)
        {
            LOGGER.error("Failed to get effect factory: {}", location);
            return null;
        }

        var effect = properties.getSupplier().create();
        effect.onStartClient();

        /*
         * Remove old items when we reached the maximum size.
         */
        if (HISTORY.size() >= MAX_HISTORY_SIZE && effect.getDurationTicks() == 0)
        {
            HISTORY.removeFirst();
        }

        EFFECTS.add(effect);
        return effect;
    }

    /**
     * Removes an active effect.
     */
    public static void removeEffect(ResourceLocation location)
    {
        for (var it = EFFECTS.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.getLocation().equals(location))
            {
                effect.onEndClient();

                /*
                 * Prevent showing ticked effects.
                 */
                if (effect.getDurationTicks() == 0)
                {
                    HISTORY.add(effect.getGUIName());
                }

                it.remove();
                break;
            }
        }
    }

    /**
     * Clears all active effects.
     */
    public static void clear()
    {
        for (var effect : EFFECTS)
        {
            effect.onEndClient();
        }

        EFFECTS.clear();
        HISTORY.clear();
    }

    /**
     * Retrieves all active effects.
     */
    public static List<Effect> getEffects()
    {
        return Collections.unmodifiableList(EFFECTS);
    }

    /**
     * Retrieves the history of effects that occurred.
     */
    public static List<String> getHistory()
    {
        return Collections.unmodifiableList(HISTORY);
    }
}
