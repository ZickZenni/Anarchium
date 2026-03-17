package com.zickzenni.anarchium.effect;

import com.zickzenni.anarchium.Anarchium;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of properties of a {@link Effect}.
 */
public class EffectProperties<T extends Effect>
{
    private final Class<T> clazz;

    private final ResourceLocation id;

    private final EffectSupplier<T> supplier;

    private final List<Class<? extends Effect>> conflicts;

    private final List<ConfigValue<?>> config;

    private final ConfigValue<Boolean> enabledValue;

    private final ConfigValue<Integer> weightValue;

    private EffectProperties(Class<T> clazz,
                             ResourceLocation id,
                             EffectSupplier<T> supplier,
                             List<Class<? extends Effect>> conflicts,
                             List<ConfigValue<?>> config)
    {
        this.clazz = clazz;
        this.id = id;
        this.supplier = supplier;
        this.conflicts = conflicts;
        this.config = config;

        this.enabledValue = ConfigValue.newBoolean("enabled", true);
        this.weightValue = ConfigValue.newInteger("weight", 1000);
        this.config.add(enabledValue);
        this.config.add(weightValue);
    }

    /**
     * Retrieves the class of the effect.
     */
    public Class<T> getClazz()
    {
        return clazz;
    }

    /**
     * Retrieves the id of the effect.
     */
    public ResourceLocation getId()
    {
        return id;
    }

    /**
     * Retrieves the supplier of the effect.
     */
    public EffectSupplier<T> getSupplier()
    {
        return supplier;
    }

    /**
     * Retrieves the list of conflicts.
     */
    @SuppressWarnings("unused")
    public List<Class<? extends Effect>> getConflicts()
    {
        return conflicts;
    }

    /**
     * Retrieves the list of config values.
     */
    public List<ConfigValue<?>> getConfig()
    {
        return config;
    }

    /**
     * Checks if the effect is enabled.
     */
    public boolean isEnabled()
    {
        return enabledValue.get();
    }

    /**
     * Retrieves the weight of the effect for selection.
     */
    public int getWeight()
    {
        return weightValue.get();
    }

    // ===================================================

    /**
     * Builder for {@link EffectProperties}.
     *
     * @param <T> type of the {@link Effect}
     */
    public static class Builder<T extends Effect>
    {
        private final Class<T> clazz;

        private ResourceLocation id;

        private EffectSupplier<T> supplier;

        private final List<Class<? extends Effect>> conflicts;

        private final List<ConfigValue<?>> config;

        private Builder(Class<T> clazz)
        {
            this.clazz = clazz;
            this.conflicts = new ArrayList<>();
            this.config = new ArrayList<>();
        }

        public static <T extends Effect> Builder<T> of(Class<T> clazz)
        {
            return new Builder<>(clazz);
        }

        public Builder<T> id(ResourceLocation id)
        {
            this.id = id;
            return this;
        }

        public Builder<T> id(String namespace, String path)
        {
            this.id = ResourceLocation.fromNamespaceAndPath(namespace, path);
            return this;
        }

        public Builder<T> id(String path)
        {
            this.id = ResourceLocation.fromNamespaceAndPath(Anarchium.MODID, path);
            return this;
        }

        public Builder<T> supplier(EffectSupplier<T> supplier)
        {
            this.supplier = supplier;
            return this;
        }

        public <C extends Effect> Builder<T> conflict(Class<C> conflict)
        {
            this.conflicts.add(conflict);
            return this;
        }

        public Builder<T> config(ConfigValue<?> value)
        {
            this.config.add(value);
            return this;
        }

        public EffectProperties<T> build()
        {
            Objects.requireNonNull(this.clazz, "Class must not be null");
            Objects.requireNonNull(this.id, "Id must not be null");

            return new EffectProperties<>(this.clazz, this.id, this.supplier, this.conflicts, this.config);
        }
    }

    // ===================================================

    /**
     * A functional interface used to supply instances of a {@link Effect}.
     *
     * @param <T> the type of {@link Effect} to be supplied
     */
    @FunctionalInterface
    public interface EffectSupplier<T extends Effect>
    {
        @SuppressWarnings("unused")
        T create();
    }
}
