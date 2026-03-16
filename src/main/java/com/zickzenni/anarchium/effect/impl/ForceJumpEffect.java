package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ForceJumpEffect extends TimedEffect
{
    public static ModConfigSpec.ConfigValue<Integer> DURATION;

    public static final EffectProperties<ForceJumpEffect> PROPERTIES =
            EffectProperties.Builder.of(ForceJumpEffect.class)
                    .id("force_jump")
                    .supplier(ForceJumpEffect::new)
                    .conflict(NoJumpingEffect.class)
                    .configure(ForceJumpEffect::configure)
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

    // ======================================================

    private static void configure(ModConfigSpec.Builder builder)
    {
        DURATION = builder.define("duration", 20 * 30);
    }
}
