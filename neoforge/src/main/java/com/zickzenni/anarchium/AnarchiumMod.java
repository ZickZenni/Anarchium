package com.zickzenni.anarchium;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.IndentStyle;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlWriter;
import com.zickzenni.anarchium.client.network.*;
import com.zickzenni.anarchium.network.*;
import com.zickzenni.anarchium.registry.EffectRegistry;
import com.zickzenni.anarchium.registry.SoundRegistry;
import com.zickzenni.anarchium.server.AnarchiumServer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.io.File;

@Mod(Constants.MOD_ID)
public class AnarchiumMod
{
    private static final String NETWORK_VERSION = "1";
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    private static final File CONFIG_FILE = new File("./config/" + Constants.MOD_ID + "-common.toml");
    public static final FileConfig CONFIG = FileConfig.of(CONFIG_FILE);
    private static final TomlWriter CONFIG_WRITER = new TomlWriter();

    public AnarchiumMod(IEventBus bus)
    {
        CONFIG_WRITER.setIndent(IndentStyle.NONE);

        SoundRegistry.DISPATCH_EFFECT_SOUND =
                SOUND_EVENTS.register("dispatch_effect", SoundEvent::createVariableRangeEvent);
        SoundRegistry.ZEUS_EFFECT_SOUND = SOUND_EVENTS.register("zeus_effect", SoundEvent::createVariableRangeEvent);

        SOUND_EVENTS.register(bus);
        CONFIG.load();

        ConfigSpec specs = new ConfigSpec();

        EffectRegistry.register();
        EffectRegistry.createConfigSpecs(specs);
        AnarchiumServer.TIMER_DURATION.configure(specs);

        specs.correct(CONFIG);
        CONFIG_WRITER.write(CONFIG, CONFIG_FILE, WritingMode.REPLACE);
    }

    @SuppressWarnings("unused")
    @EventBusSubscriber(modid = Constants.MOD_ID)
    private static class CommonEvents
    {
        @SubscribeEvent
        public static void registerPayload(RegisterPayloadHandlersEvent event)
        {
            final var registrar = event.registrar(NETWORK_VERSION);

            registrar.playToClient(
                    ActivateEffectPacket.TYPE,
                    ActivateEffectPacket.STREAM_CODEC,
                    NeoForgeActivateEffectPacketHandler::handle
            );

            registrar.playToClient(
                    EndEffectPacket.TYPE,
                    EndEffectPacket.STREAM_CODEC,
                    NeoForgeEndEffectPacketHandler::handle
            );

            registrar.playToClient(
                    TickEffectPacket.TYPE,
                    TickEffectPacket.STREAM_CODEC,
                    NeoForgeTickEffectPacketHandler::handle
            );

            registrar.playToClient(
                    TimerTickPacket.TYPE,
                    TimerTickPacket.STREAM_CODEC,
                    NeoForgeTimerTickPacketHandler::handle
            );

            registrar.playToClient(
                    ConfigurationPacket.TYPE,
                    ConfigurationPacket.STREAM_CODEC,
                    NeoForgeConfigurationPacketHandler::handle
            );
        }
    }
}