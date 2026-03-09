package com.zickzenni.anarchium.network.packets;

import com.zickzenni.anarchium.Anarchium;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record EndEffectPacket(String id) implements CustomPacketPayload
{
    public static final Type<EndEffectPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Anarchium.MODID, "end_effect"));

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