package com.zickzenni.anarchium.server;

import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.effect.IEffectHandler;
import com.zickzenni.anarchium.util.Environment;
import net.minecraft.resources.Identifier;

public class ServerEffectRegistry extends EffectRegistry
{
    public static void registerHandler(Identifier identifier, Class<? extends IEffectHandler> handler)
    {
        EffectRegistry.registerHandler(identifier, handler, Environment.SERVER);
    }
}
