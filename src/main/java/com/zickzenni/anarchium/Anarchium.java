package com.zickzenni.anarchium;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.effect.BaseEffect;
import com.zickzenni.anarchium.effect.EffectDescription;
import com.zickzenni.anarchium.effect.ReversedGravityEffect;
import com.zickzenni.anarchium.registry.EffectRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@Mod(Anarchium.MODID)
public class Anarchium
{
    public static final String MODID = "anarchium";

    public static final Logger LOGGER = LogUtils.getLogger();

    private final ArrayList<BaseEffect> activeEffects = new ArrayList<>();

    private int ticksTilEffect = 200;

    public Anarchium(IEventBus eventBus)
    {
        eventBus.addListener(this::commonSetup);

        EffectRegistry.register(ReversedGravityEffect.class, ReversedGravityEffect.DESCRIPTION);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @SubscribeEvent
    public void onLevelTickPre(LevelTickEvent.Pre event)
    {
        if (event.getLevel().isClientSide())
        {
            return;
        }

        for (BaseEffect effect : activeEffects)
        {
            if (effect.description.ticks > 0)
            {
                effect.onLevelTick(event.getLevel(), true);
            }
        }
    }

    @SubscribeEvent
    public void onLevelTickPost(LevelTickEvent.Post event)
    {
        if (event.getLevel().isClientSide())
        {
            return;
        }

        for (Iterator<BaseEffect> it = activeEffects.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.description.ticks > 0)
            {
                effect.onLevelTick(event.getLevel(), false);
            } else
            {
                it.remove();
            }
        }

        if (ticksTilEffect > 0)
        {
            ticksTilEffect--;
        } else
        {
            ticksTilEffect = 20 * 10;

            var effects = EffectRegistry.getItems().toArray();
            EffectRegistry.RegistryItem randomEffect = (EffectRegistry.RegistryItem) effects[new Random().nextInt(effects.length)];

            LOGGER.info("[Anarchium] Picked new effect: {}", randomEffect.description().id);

            for (BaseEffect activeEffect : activeEffects)
            {
                if (activeEffect.description.id.equals(randomEffect.description().id))
                {
                    activeEffect.resetTicks();
                    break;
                }
            }

            try
            {
                var instance = randomEffect.clazz().getConstructor(EffectDescription.class).newInstance(randomEffect.description());

                if (randomEffect.description().tickable)
                {
                    activeEffects.add(instance);
                } else
                {
                    instance.onPick(event.getLevel());
                }
            } catch (Exception e)
            {
                LOGGER.error("[Anarchium] Failed to create instance of effect {}: {}", randomEffect.description().id, e);
            }
        }
    }
}
