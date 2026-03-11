package com.zickzenni.anarchium.effect.impl;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.Anarchium;
import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.effect.EffectSupplier;
import com.zickzenni.anarchium.effect.InstantEffect;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

public class LargeEntitiesEffect extends TimedEffect
{
    public static final EffectSupplier<LargeEntitiesEffect> SUPPLIER = LargeEntitiesEffect::new;

    public static final Identifier ID = Anarchium.identifier("large_entities");

    public LargeEntitiesEffect()
    {
        super(ID, 20 * 45);
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableLargeEntities = true;
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableLargeEntities = false;
    }
}
