package com.zickzenni.anarchium.effect;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Optional;

public abstract class ConfigValue<T>
{
    public enum Type
    {
        INTEGER, DOUBLE, BOOLEAN
    }

    // ================================================

    protected final String name;

    protected final T defaultValue;

    protected ModConfigSpec.ConfigValue<T> spec;

    protected T value;

    private ConfigValue(String name, T defaultValue)
    {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    // ================================================

    public static IntegerValue newInteger(String name, int defaultValue)
    {
        return new IntegerValue(name, defaultValue);
    }

    public static DoubleValue newDouble(String name, double defaultValue)
    {
        return new DoubleValue(name, defaultValue);
    }

    public static DoubleValue newDoubleInRange(
            String name,
            double defaultValue,
            double min,
            double max)
    {
        return new DoubleValue(name, defaultValue, min, max);
    }

    public static BooleanValue newBoolean(String name, boolean defaultValue)
    {
        return new BooleanValue(name, defaultValue);
    }

    // ================================================

    public abstract void configure(ModConfigSpec.Builder builder);

    public abstract void setRaw(Object value);

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

    public void load()
    {
        this.value = this.spec.get();
    }

    public abstract Type getType();

    // ================================================

    public static class IntegerValue extends ConfigValue<Integer>
    {
        IntegerValue(String name, int defaultValue)
        {
            super(name, defaultValue);
        }

        @Override
        public void configure(ModConfigSpec.Builder builder)
        {
            this.spec = builder.define(this.name, this.defaultValue);
        }

        @Override
        public void setRaw(Object value)
        {
            if (value instanceof Integer)
            {
                this.value = (Integer) value;
            } else
            {
                throw new IllegalArgumentException("Value must be an integer");
            }
        }

        @Override
        public Type getType()
        {
            return Type.INTEGER;
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class DoubleValue extends ConfigValue<Double>
    {
        private final Optional<Double> min;

        private final Optional<Double> max;

        DoubleValue(String name, double defaultValue)
        {
            super(name, defaultValue);
            this.min = Optional.empty();
            this.max = Optional.empty();
        }

        DoubleValue(String name, double defaultValue, double min, double max)
        {
            super(name, defaultValue);
            this.min = Optional.of(min);
            this.max = Optional.of(max);
        }

        @Override
        public void configure(ModConfigSpec.Builder builder)
        {
            if (this.min.isPresent() && this.max.isPresent())
            {
                this.spec = builder.defineInRange(this.name, this.defaultValue, this.min.get(), this.max.get());
            } else if (this.min.isPresent())
            {
                this.spec = builder.defineInRange(this.name, this.defaultValue, this.min.get(), Double.MAX_VALUE);
            } else if (this.max.isPresent())
            {
                this.spec = builder.defineInRange(this.name, this.defaultValue, Double.MIN_VALUE, this.max.get());
            } else
            {
                this.spec = builder.define(this.name, this.defaultValue);
            }
        }

        @Override
        public void setRaw(Object value)
        {
            if (value instanceof Double)
            {
                this.value = (Double) value;
            } else
            {
                throw new IllegalArgumentException("Value must be a double");
            }
        }

        @Override
        public Type getType()
        {
            return Type.DOUBLE;
        }
    }

    public static class BooleanValue extends ConfigValue<Boolean>
    {
        BooleanValue(String name, boolean defaultValue)
        {
            super(name, defaultValue);
        }

        @Override
        public void configure(ModConfigSpec.Builder builder)
        {
            this.spec = builder.define(this.name, this.defaultValue);
        }

        @Override
        public void setRaw(Object value)
        {
            if (value instanceof Boolean)
            {
                this.value = (Boolean) value;
            } else
            {
                throw new IllegalArgumentException("Value must be a boolean");
            }
        }

        @Override
        public Type getType()
        {
            return Type.BOOLEAN;
        }
    }
}
