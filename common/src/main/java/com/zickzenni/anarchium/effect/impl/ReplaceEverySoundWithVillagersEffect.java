package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;

public class ReplaceEverySoundWithVillagersEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);
    public static final EffectProperties<ReplaceEverySoundWithVillagersEffect> PROPERTIES =
            EffectProperties.Builder.of(ReplaceEverySoundWithVillagersEffect.class)
                    .id("replace_every_sound_with_villagers")
                    .supplier(ReplaceEverySoundWithVillagersEffect::new)
                    .config(DURATION)
                    .build();
    private static final RandomSource RANDOM = RandomSource.create();

    public static boolean ENABLED = false;

    public ReplaceEverySoundWithVillagersEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    public static Sound replaceSound$SoundEngine()
    {
        var soundEvents =
                Minecraft.getInstance().getSoundManager().getSoundEvent(SoundEvents.VILLAGER_AMBIENT.getLocation());
        return soundEvents != null ? soundEvents.getSound(RANDOM) : SoundManager.EMPTY_SOUND;
    }

    @Override
    public void onStartClient()
    {
        ENABLED = true;
    }

    @Override
    public void onEndClient()
    {
        ENABLED = false;
    }
}
