package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.effect.EffectInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.lang.reflect.InvocationTargetException;

public class ClientPayloadHandler
{
    public static void handleActivateEffect(final ActivateEffectPacket data, final IPayloadContext ignoredCtx)
    {
        var player = Minecraft.getInstance().player;

        if (player == null)
        {
            return;
        }

        var identifier = Identifier.tryParse(data.id());

        if (identifier == null)
        {
            AnarchiumClient.LOGGER.error("[Anarchium] Failed to parse effect identifier {}", data.id());
            return;
        }

        player.displayClientMessage(Component.literal("Activated effect: " + data.id()), true);

        var effect = EffectRegistry.getHandler(identifier, EffectRegistry.Side.CLIENT);
        var description = EffectRegistry.getDescription(identifier);

        if (effect == null || description == null)
        {
            AnarchiumClient.LOGGER.error("[Anarchium] Failed to find effect {} on client", identifier);
            return;
        }

        for (var activeEffect : AnarchiumClient.getInstance().activeEffects)
        {
            if (activeEffect.identifier.equals(identifier) && !activeEffect.indefinite)
            {
                activeEffect.ticks = description.getDurationTicks();
                return;
            }
        }

        try
        {
            var handler = effect.getConstructor().newInstance();

            if (description.isTickable() || description.isIndefinite())
            {
                var instance = new EffectInstance(identifier, handler);

                if (description.isTickable())
                {
                    instance.ticks = description.getDurationTicks();
                } else
                {
                    instance.indefinite = true;
                }

                AnarchiumClient.getInstance().activeEffects.add(instance);
            } else
            {
                handler.onStart();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e)
        {
            AnarchiumClient.LOGGER.error("[Anarchium] Failed to create instance of effect {}: {}", identifier, e);
        }
    }
}
