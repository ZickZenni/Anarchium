package com.zickzenni.anarchium.config;

import com.electronwill.nightconfig.core.ConfigSpec;

public class BooleanValue extends ConfigValue<Boolean>
{
    BooleanValue(String name, boolean defaultValue)
    {
        super(name, defaultValue);
    }

    @Override
    public void configure(ConfigSpec builder)
    {
        builder.define(this.name, this.defaultValue);
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
    public ValueType getType()
    {
        return ValueType.BOOLEAN;
    }
}