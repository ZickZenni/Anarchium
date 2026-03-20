package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.ConfigValue;
import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.TimedEffect;
import com.zickzenni.anarchium.util.function.Double3Consumer;
import com.zickzenni.anarchium.util.function.Float3Consumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class GTA2Effect extends TimedEffect
{
    public static final ConfigValue<Integer> DURATION = ConfigValue.newInteger("duration", 20 * 55);
    public static final ConfigValue<Double> CAMERA_DISTANCE =
            ConfigValue.newDoubleInRange("camera_distance", 10, 0.0, 250.0);
    public static final EffectProperties<GTA2Effect> PROPERTIES =
            EffectProperties.Builder.of(GTA2Effect.class)
                    .id("gta2")
                    .supplier(GTA2Effect::new)
                    .config(DURATION)
                    .config(CAMERA_DISTANCE)
                    .build();

    public static boolean ENABLED = false;

    public GTA2Effect()
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

    public static void updateCamera$Camera(Entity entity,
                                           float partialTick,
                                           Double3Consumer setPosition,
                                           Float3Consumer setRotation)
    {
        var distance = CAMERA_DISTANCE.get();

        setPosition.accept(
                Mth.lerp(partialTick, entity.xo, entity.getX()),
                Mth.lerp(partialTick, entity.yo + distance, entity.getY() + distance),
                Mth.lerp(partialTick, entity.zo, entity.getZ())
        );
        setRotation.accept(0, 90.0f, 0);
    }
}
