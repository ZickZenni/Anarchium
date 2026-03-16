package com.zickzenni.anarchium.network.packet;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.ConfigValue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record ConfigurationPacket(List<Effect> effects) implements CustomPacketPayload
{
    public record Entry(String id, ConfigValue.Type type, Object value) {}

    public record Effect(String id, List<Entry> entries) {}

    // ==========================================================

    public static final Type<ConfigurationPacket> TYPE = new Type<>(Anarchium.location("configuration"));

    public static final StreamCodec<FriendlyByteBuf, ConfigurationPacket> STREAM_CODEC =
            StreamCodec.of(ConfigurationPacket::encode, ConfigurationPacket::decode);

    private static void encode(FriendlyByteBuf buf, ConfigurationPacket payload)
    {
        buf.writeInt(payload.effects.size());

        for (var effect : payload.effects)
        {
            buf.writeUtf(effect.id);
            buf.writeInt(effect.entries.size());

            for (var entry : effect.entries)
            {
                buf.writeUtf(entry.id);
                buf.writeEnum(entry.type);

                switch (entry.type)
                {
                    case INTEGER -> buf.writeInt((int) entry.value);
                    case DOUBLE -> buf.writeDouble((double) entry.value);
                    case BOOLEAN -> buf.writeBoolean((boolean) entry.value);
                }
            }
        }
    }

    private static ConfigurationPacket decode(FriendlyByteBuf buf)
    {
        var size = buf.readInt();
        var effects = new ArrayList<Effect>();

        for (int i = 0; i < size; i++)
        {
            var effectId = buf.readUtf();
            var size2 = buf.readInt();

            var entries = new ArrayList<Entry>();

            for (int j = 0; j < size2; j++)
            {
                var entryId = buf.readUtf();
                var type = buf.readEnum(ConfigValue.Type.class);

                var value = switch (type)
                {
                    case INTEGER -> buf.readInt();
                    case DOUBLE -> buf.readDouble();
                    case BOOLEAN -> buf.readBoolean();
                };

                entries.add(new Entry(entryId, type, value));
            }

            effects.add(new Effect(effectId, entries));
        }

        return new ConfigurationPacket(effects);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}