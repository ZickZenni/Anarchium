package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.effect.EffectInstance;
import com.zickzenni.anarchium.effect.EffectRegistry;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import com.zickzenni.anarchium.util.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
            AnarchiumClient.LOGGER.error("Failed to parse effect identifier {}", data.id());
            return;
        }

        player.displayClientMessage(Component.literal("Activated effect: " + data.id()), true);

        var handler = EffectRegistry.getHandler(identifier, Environment.CLIENT);

        if (handler == null)
        {
            AnarchiumClient.LOGGER.warn("Effect {} does not have a client-bound handler", identifier);
            return;
        }

        var properties = EffectRegistry.getDescription(identifier);

        if (properties == null)
        {
            AnarchiumClient.LOGGER.error("Failed to find properties for effect {} on client", identifier);
            return;
        }

        AnarchiumClient.getInstance().effectManager.createEffect(identifier, handler, properties);
    }

    public static void handleEndEffect(final EndEffectPacket data, final IPayloadContext ignoredCtx)
    {
        var identifier = Identifier.tryParse(data.id());

        if (identifier == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect identifier {}", data.id());
            return;
        }

        AnarchiumClient.getInstance().effectManager.removeEffect(identifier);
    }

    public static void handleTickEffect(final TickEffectPacket data, IPayloadContext ignoredCtx)
    {
        var identifier = Identifier.tryParse(data.id());

        if (identifier == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect identifier {}", data.id());
            return;
        }

        var effectManager = AnarchiumClient.getInstance().effectManager;

        for (var effect : effectManager.effects)
        {
            if (effect.identifier.equals(identifier))
            {
                effect.ticks = data.ticks();
                break;
            }
        }
    }

    public static void handleTimerTick(final TimerTickPacket data, final IPayloadContext ignoredCtx)
    {
        AnarchiumClient.getInstance().timerTicks = data.ticks();
        AnarchiumClient.getInstance().timerDuration = data.duration();
    }
}
