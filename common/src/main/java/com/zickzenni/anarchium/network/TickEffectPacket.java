package com.zickzenni.anarchium.network;

import com.zickzenni.anarchium.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TickEffectPacket(String id, int ticks) implements CustomPacketPayload
{
    public static final Type<TickEffectPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "tick_effect"));

    public static final StreamCodec<ByteBuf, TickEffectPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            TickEffectPacket::id,
            ByteBufCodecs.INT,
            TickEffectPacket::ticks,
            TickEffectPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}