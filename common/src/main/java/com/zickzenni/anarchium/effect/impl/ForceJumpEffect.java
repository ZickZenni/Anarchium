package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
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

    public ForceJumpEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onLevelTickClient(ILevelTickEvent<ClientLevel> event)
    {
        if (event.getStage() == ILevelTickEvent.Stage.PRE)
        {
            var player = Minecraft.getInstance().player;

            if (player != null && player.onGround())
            {
                player.jumpFromGround();
            }
        }
        super.onLevelTickClient(event);
    }
}
