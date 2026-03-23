package com.zickzenni.anarchium.config;

import com.electronwill.nightconfig.core.ConfigSpec;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DoubleValue extends ConfigValue<Double>
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
    public void configure(ConfigSpec builder)
    {
        if (this.min.isPresent() && this.max.isPresent())
        {
            builder.defineInRange(this.name, this.defaultValue, this.min.get(), this.max.get());
        } else if (this.min.isPresent())
        {
            builder.defineInRange(this.name, this.defaultValue, this.min.get(), Double.MAX_VALUE);
        } else if (this.max.isPresent())
        {
            builder.defineInRange(this.name, this.defaultValue, Double.MIN_VALUE, this.max.get());
        } else
        {
            builder.define(this.name, this.defaultValue);
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
    public ValueType getType()
    {
        return ValueType.DOUBLE;
    }
}