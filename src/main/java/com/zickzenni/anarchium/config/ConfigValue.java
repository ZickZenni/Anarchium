package com.zickzenni.anarchium.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public abstract class ConfigValue<T>
{
    protected final String name;

    protected final T defaultValue;

    protected ModConfigSpec.ConfigValue<T> spec;

    protected T value;

    ConfigValue(String name, T defaultValue)
    {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new integer config value.
     */
    public static IntegerValue newInteger(String name, int defaultValue)
    {
        return new IntegerValue(name, defaultValue);
    }

    /**
     * Creates a new double config value.
     */
    public static DoubleValue newDouble(String name, double defaultValue)
    {
        return new DoubleValue(name, defaultValue);
    }

    /**
     * Creates a new double config value with a range.
     */
    public static DoubleValue newDoubleInRange(
            String name,
            double defaultValue,
            double min,
            double max)
    {
        return new DoubleValue(name, defaultValue, min, max);
    }

    /**
     * Creates a new boolean config value.
     */
    public static BooleanValue newBoolean(String name, boolean defaultValue)
    {
        return new BooleanValue(name, defaultValue);
    }

    /**
     * Loads the value from the config file.
     */
    public void load()
    {
        this.value = this.spec.get();
    }

    /**
     * Configures the value in the config spec.
     */
    public abstract void configure(ModConfigSpec.Builder builder);

    public String getName()
    {
        return name;
    }

    public T get()
    {
        return value;
    }

    public Object getRaw()
    {
        return value;
    }

    public abstract void setRaw(Object value);

    public abstract ValueType getType();
}
