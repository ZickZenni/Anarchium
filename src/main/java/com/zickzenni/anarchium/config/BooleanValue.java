package com.zickzenni.anarchium.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BooleanValue extends ConfigValue<Boolean>
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
    public ValueType getType()
    {
        return ValueType.BOOLEAN;
    }
}