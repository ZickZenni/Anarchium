package com.zickzenni.anarchium.network.packets;

import com.zickzenni.anarchium.Anarchium;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ActivateEffectPacket(String id) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<ActivateEffectPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(Anarchium.MODID, "activate_effect"));

    public static final StreamCodec<ByteBuf, ActivateEffectPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ActivateEffectPacket::id,
            ActivateEffectPacket::new
    );

    @Override
    public CustomPacketPayload.@NonNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}