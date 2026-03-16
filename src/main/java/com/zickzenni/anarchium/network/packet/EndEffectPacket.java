package com.zickzenni.anarchium.network.packet;

import com.zickzenni.anarchium.Anarchium;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record EndEffectPacket(String id) implements CustomPacketPayload
{
    public static final Type<EndEffectPacket> TYPE = new Type<>(Anarchium.location("end_effect"));

    public static final StreamCodec<ByteBuf, EndEffectPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            EndEffectPacket::id,
            EndEffectPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}