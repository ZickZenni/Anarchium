package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.TimedEffect;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;

public class ForceJumpEffect extends TimedEffect
{
    public static final EffectSupplier<ForceJumpEffect> SUPPLIER = ForceJumpEffect::new;

    public static final ResourceLocation ID = Anarchium.location("force_jump");

    public ForceJumpEffect()
    {
        super(ID, 20 * 27);
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
