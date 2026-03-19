package com.zickzenni.anarchium.effect.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import net.minecraft.client.Minecraft;

public class FlatWorldEffect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 45);
    public static final EffectProperties<FlatWorldEffect> PROPERTIES =
            EffectProperties.Builder.of(FlatWorldEffect.class)
                    .id("flat_world")
                    .supplier(FlatWorldEffect::new)
                    .conflict(WhereAreMyChunksEffect.class)
                    .config(DURATION)
                    .build();

    public static boolean ENABLED = false;

    public FlatWorldEffect()
    {
        super(PROPERTIES.getId(), DURATION.get());
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

    public static void modifyChunkModelView(PoseStack stack)
    {
        var camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        var camPos = camera.getPosition();
        var entity = camera.getEntity();

        if (camera.isDetached())
        {
            stack.translate(0, ((entity.yo + entity.getEyeHeight()) - camPos.y), 0);
        }

        stack.translate(0.0F, -1.7, 0.0F);
        stack.scale(1.0F, 0.01f, 1.0F);
    }
}
