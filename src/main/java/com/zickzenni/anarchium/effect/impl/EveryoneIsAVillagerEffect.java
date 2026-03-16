package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.TimedEffect;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.world.entity.npc.Villager;

public class EveryoneIsAVillagerEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 50);

    public static final EffectProperties<EveryoneIsAVillagerEffect> PROPERTIES =
            EffectProperties.Builder.of(EveryoneIsAVillagerEffect.class)
                    .id("everyone_is_a_villager")
                    .supplier(EveryoneIsAVillagerEffect::new)
                    .config(DURATION)
                    .build();

    // ======================================================

    public static VillagerModel<Villager> VILLAGER_MODEL;

    public static boolean ENABLED = false;

    public EveryoneIsAVillagerEffect()
    {
        super(PROPERTIES.getId(), 20 * 50);
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
