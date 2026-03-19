package com.zickzenni.anarchium.network.packet;

import com.zickzenni.anarchium.Anarchium;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record TickEffectPacket(String id, int ticks) implements CustomPacketPayload
{
    public static final Type<TickEffectPacket> TYPE = new Type<>(Anarchium.getLocation("tick_effect"));

    public static final StreamCodec<ByteBuf, TickEffectPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            TickEffectPacket::id,
            ByteBufCodecs.INT,
            TickEffectPacket::ticks,
            TickEffectPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}