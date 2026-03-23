package com.zickzenni.anarchium.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigSpec;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigValue<T>
{
    protected final String name;

    protected final T defaultValue;

    protected T value;

    protected String prefix;

    ConfigValue(String name, T defaultValue)
    {
        this.name = name;
        this.defaultValue = defaultValue;
        this.prefix = null;
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
    public void load(Config config)
    {
        this.value = config.get(this.getPath());
    }

    public void setPrefix(@Nullable String prefix)
    {
        this.prefix = prefix;
    }

    public String getPath()
    {
        return this.prefix == null ? this.name : this.prefix + "." + this.name;
    }

    /**
     * Configures the value in the config spec.
     */
    public abstract void configure(ConfigSpec builder);

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
