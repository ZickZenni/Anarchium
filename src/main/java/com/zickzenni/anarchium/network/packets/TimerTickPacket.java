package com.zickzenni.anarchium.network.packets;

import com.zickzenni.anarchium.Anarchium;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record TimerTickPacket(int ticks, int duration) implements CustomPacketPayload
{
    public static final Type<TimerTickPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Anarchium.MODID, "timer_tick"));

    public static final StreamCodec<ByteBuf, TimerTickPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            TimerTickPacket::ticks,
            ByteBufCodecs.INT,
            TimerTickPacket::duration,
            TimerTickPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}