package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.client.EffectStates;
import com.zickzenni.anarchium.config.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.event.IRenderLevelEvent;
import net.minecraft.client.Minecraft;

public class NegativeFieldOfViewEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 15);
    public static final EffectProperties<NegativeFieldOfViewEffect> PROPERTIES =
            EffectProperties.Builder.of(NegativeFieldOfViewEffect.class)
                    .id("negative_field_of_view")
                    .supplier(NegativeFieldOfViewEffect::new)
                    .conflict(QuakeFieldOfViewEffect.class)
                    .config(DURATION)
                    .build();

    public NegativeFieldOfViewEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
    }

    @Override
    public void onStartClient()
    {
        EffectStates.enableCustomFOV = true;
        EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
    }

    @Override
    public void onEndClient()
    {
        EffectStates.enableCustomFOV = false;
    }

    @Override
    public void onRenderLevel(IRenderLevelEvent event)
    {
        if (event.getStage() == IRenderLevelEvent.Stage.START)
        {
            EffectStates.customFOV = -Minecraft.getInstance().options.fov().get();
        }
    }
}

