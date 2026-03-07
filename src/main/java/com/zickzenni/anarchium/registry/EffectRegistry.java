package com.zickzenni.anarchium.registry;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.BaseEffect;
import com.zickzenni.anarchium.effect.EffectDescription;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

public class EffectRegistry
{
    public record RegistryItem(Class<? extends BaseEffect> clazz, EffectDescription description)
    {}

    private static final HashMap<String, RegistryItem> REGISTRY = new HashMap<>();

    /**
     * Registers a new effect along with its associated description in the effect registry.
     */
    public static void register(Class<? extends BaseEffect> effect, EffectDescription description)
    {
        if (REGISTRY.containsKey(effect.getName()))
        {
            throw new IllegalStateException("Duplicate effect name: " + effect.getName());
        }

        REGISTRY.put(description.id, new RegistryItem(effect, description));
        Anarchium.LOGGER.info("[EffectRegistry] Registered effect: {} [{}]", description.id, effect.getName());
    }

    /**
     * Retrieves the registered effect by its ID.
     */
    @Nullable
    public static RegistryItem getItem(String id)
    {
        return REGISTRY.getOrDefault(id, null);
    }

    /**
     * Retrieves all registered effects.
     */
    public static Collection<RegistryItem> getItems() {
        return REGISTRY.values();
    }
}
