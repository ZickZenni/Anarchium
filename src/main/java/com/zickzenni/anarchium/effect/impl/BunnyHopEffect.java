package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class BunnyHopEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<BunnyHopEffect> PROPERTIES = EffectProperties.Builder.of(BunnyHopEffect.class)
            .id("bunny_hop")
            .supplier(BunnyHopEffect::new)
            .config(DURATION)
            .build();

    private boolean moved = false;

    public BunnyHopEffect()
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

            var input = player.input.getMoveVector();

            if (input.length() <= 0.1)
            {
                if (moved)
                {
                    player.setDeltaMovement(0, 0, 0);
                    moved = false;
                }
                return;
            }

            var deltaMovement = player.getDeltaMovement();

            if (!player.onGround())
            {
                var yRot = player.getYRot();
                var moveX = Mth.cos(yRot * (float) (Math.PI / 180.0));
                var moveZ = Mth.sin(yRot * (float) (Math.PI / 180.0));
                var speed = 0.345f;
                var movement =
                        new Vec3(
                                (input.x * moveX - input.y * moveZ) * speed,
                                deltaMovement.y,
                                (input.y * moveX + input.x * moveZ) * speed
                        );

                player.setDeltaMovement(movement);
            } else
            {
                player.setDeltaMovement(deltaMovement.x, 0.4f, deltaMovement.z);
            }

            moved = true;
        }

        super.onLevelTickClient(level, stage);
    }
}
