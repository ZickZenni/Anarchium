package com.zickzenni.anarchium.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ReversedGravityEffect extends BaseEffect
{
    public static final EffectDescription DESCRIPTION = new EffectDescription("reversed_gravity", 100);

    public ReversedGravityEffect(EffectDescription description)
    {
        super(description);
    }

    @Override
    public void onLevelTickPost(Level level)
    {
        if (level.isClientSide())
        {
            return;
        }

        var serverLevel = (ServerLevel) level;

        for (Entity entity : serverLevel.getAllEntities())
        {
            double gravity = entity.getGravity();

            if (gravity != (double) 0.0F)
            {
                var dM = entity.getDeltaMovement();
                entity.setDeltaMovement(dM.x, gravity * 2, dM.z);
            }
        }
    }
}
