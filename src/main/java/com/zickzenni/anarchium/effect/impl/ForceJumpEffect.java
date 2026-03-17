package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ForceJumpEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 30);

    public static final EffectProperties<ForceJumpEffect> PROPERTIES =
            EffectProperties.Builder.of(ForceJumpEffect.class)
                    .id("force_jump")
                    .supplier(ForceJumpEffect::new)
                    .conflict(NoJumpingEffect.class)
                    .config(DURATION)
                    .build();

    // ======================================================

    public ForceJumpEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickClient(ClientLevel level, LevelTickStage stage)
    {
        if (stage == LevelTickStage.PRE)
        {
            var player = Minecraft.getInstance().player;

            if (player != null && player.onGround())
            {
                player.jumpFromGround();
            }
        }
        super.onLevelTickClient(level, stage);
    }
}
