package com.zickzenni.anarchium.effect;

@FunctionalInterface
public interface EffectSupplier<T extends Effect>
{
    T create();
}