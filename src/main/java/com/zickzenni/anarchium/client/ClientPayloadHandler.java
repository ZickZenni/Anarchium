package com.zickzenni.anarchium.client;

import com.zickzenni.anarchium.effect.Effect;
import com.zickzenni.anarchium.network.packets.ActivateEffectPacket;
import com.zickzenni.anarchium.network.packets.EndEffectPacket;
import com.zickzenni.anarchium.network.packets.TickEffectPacket;
import com.zickzenni.anarchium.network.packets.TimerTickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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

        var location = ResourceLocation.tryParse(data.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", data.id());
            return;
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            if (effect.getLocation().equals(location))
            {
                player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
                AnarchiumClient.getInstance().timerTicks = AnarchiumClient.getInstance().timerDuration;
                return;
            }
        }

        var effect = ClientEffectManager.createEffect(location);

        if (effect != null)
        {
            player.playSound(effect.getDispatchSound().value(), 1.0f, 1.0f);
        }

        AnarchiumClient.getInstance().timerTicks = AnarchiumClient.getInstance().timerDuration;
    }

    public static void handleEndEffect(final EndEffectPacket data, final IPayloadContext ignoredCtx)
    {
        var location = ResourceLocation.tryParse(data.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", data.id());
            return;
        }

        ClientEffectManager.removeEffect(location);
    }

    public static void handleTickEffect(final TickEffectPacket data, IPayloadContext ignoredCtx)
    {
        var location = ResourceLocation.tryParse(data.id());

        if (location == null)
        {
            AnarchiumClient.LOGGER.error("Failed to parse effect location {}", data.id());
            return;
        }

        for (var effect : ClientEffectManager.getEffects())
        {
            if (effect.getLocation().equals(location))
            {
                effect.setTicks(data.ticks());
                break;
            }
        }
    }

    public static void handleTimerTick(final TimerTickPacket data, final IPayloadContext ignoredCtx)
    {
        var instance = AnarchiumClient.getInstance();
        var diff = instance.timerTicks - data.ticks();

        /*
         * Correct prediction from the client.
         *
         * 11 because of 10 prediction ticks and +1
         */
        if (diff <= (data.duration() - 11) && diff >= (data.duration() - 11))
        {
            instance.timerTicks = data.ticks();
        }

        instance.timerDuration = data.duration();
        instance.predictTicks = 10;
    }
}
