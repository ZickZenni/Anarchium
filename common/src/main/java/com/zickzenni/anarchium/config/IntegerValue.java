package com.zickzenni.anarchium.config;

import com.electronwill.nightconfig.core.ConfigSpec;

public class IntegerValue extends ConfigValue<Integer>
{
    IntegerValue(String name, int defaultValue)
    {
        super(name, defaultValue);
    }

    @Override
    public void configure(ConfigSpec builder)
    {
        var path = this.getPath();

        builder.define(path, this.defaultValue);
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
    public ValueType getType()
    {
        return ValueType.INTEGER;
    }
}