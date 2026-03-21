package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class PTSDEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 35);
    public static final EffectProperties<PTSDEffect> PROPERTIES =
            EffectProperties.Builder.of(PTSDEffect.class)
                    .id("ptsd")
                    .supplier(PTSDEffect::new)
                    .config(DURATION)
                    .build();

    private static final RandomSource RANDOM = RandomSource.create();

    private int ticks;

    public PTSDEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            var player = Minecraft.getInstance().player;
            assert player != null;

            if (ticks <= 0)
            {
                var posX = RANDOM.nextInt(3) - RANDOM.nextInt(3);
                var posY = RANDOM.nextInt(2) - RANDOM.nextInt(2);
                var posZ = RANDOM.nextInt(3) - RANDOM.nextInt(3);
                var position = player.blockPosition().offset(posX, posY, posZ);

                level.playLocalSound(
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        SoundEvents.CREEPER_PRIMED,
                        SoundSource.HOSTILE,
                        1.0f,
                        0.5f,
                        false
                );

                ticks = RANDOM.nextIntBetweenInclusive(70, 120);
            } else
            {
                ticks--;
            }
        }

        super.onLevelTickClient(level, stage);
    }
}
