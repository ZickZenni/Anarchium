package com.zickzenni.anarchium.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class IntegerValue extends ConfigValue<Integer>
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
    public ValueType getType()
    {
        return ValueType.INTEGER;
    }
}