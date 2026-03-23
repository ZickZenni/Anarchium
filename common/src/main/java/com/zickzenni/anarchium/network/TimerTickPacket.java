package com.zickzenni.anarchium.network;

import com.zickzenni.anarchium.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TimerTickPacket(int ticks, int duration) implements CustomPacketPayload
{
    public static final Type<TimerTickPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "timer_tick"));

    public static final StreamCodec<ByteBuf, TimerTickPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            TimerTickPacket::ticks,
            ByteBufCodecs.INT,
            TimerTickPacket::duration,
            TimerTickPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}