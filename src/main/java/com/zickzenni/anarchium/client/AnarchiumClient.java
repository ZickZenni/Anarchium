package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.effect.ClientReversedGravityEffect;
import com.zickzenni.anarchium.effect.EffectIdentifiers;
import com.zickzenni.anarchium.effect.EffectInstance;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AnarchiumClient
{
    private static final AnarchiumClient INSTANCE = new AnarchiumClient();

    public static final Logger LOGGER = LogUtils.getLogger();

    final List<EffectInstance> activeEffects;

    public int timerTicks;

    public int timerDuration;

    private AnarchiumClient()
    {
        this.activeEffects = new ArrayList<>();
    }

    public static void setup()
    {
        ClientEffectRegistry.registerHandler(
                EffectIdentifiers.REVERSED_GRAVITY,
                ClientReversedGravityEffect.class
        );
    }

    public void processLevelTick(ClientLevel level, LevelTickStage stage)
    {
        switch (stage)
        {
            case Pre:
                processPreLevelTick(level);
                break;
            case Post:
                processPostLevelTick(level);
                break;
        }
    }

    private void processPreLevelTick(ClientLevel level)
    {
        for (var effect : activeEffects)
        {
            if (effect.ticks > 0 || (effect.indefinite && !effect.handler.hasIndefiniteEnded()))
            {
                effect.handler.onLevelTick(level, LevelTickStage.Pre);
            }
        }
    }

    private void processPostLevelTick(ClientLevel level)
    {
        for (var it = activeEffects.iterator(); it.hasNext(); )
        {
            var effect = it.next();

            if (effect.ticks > 0)
            {
                effect.handler.onLevelTick(level, LevelTickStage.Post);
                effect.ticks--;
            } else if (effect.indefinite && !effect.handler.hasIndefiniteEnded())
            {
                effect.handler.onLevelTick(level, LevelTickStage.Post);
            } else
            {
                it.remove();
            }
        }
    }

    public static AnarchiumClient getInstance()
    {
        return INSTANCE;
    }
}
