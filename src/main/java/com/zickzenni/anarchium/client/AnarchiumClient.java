package com.zickzenni.anarchium.client;

import com.mojang.logging.LogUtils;
import com.zickzenni.anarchium.client.effect.ClientReversedGravityEffect;
import com.zickzenni.anarchium.effect.EffectIdentifiers;
import com.zickzenni.anarchium.effect.EffectManager;
import com.zickzenni.anarchium.util.LevelTickStage;
import net.minecraft.client.multiplayer.ClientLevel;
import org.slf4j.Logger;

public class AnarchiumClient
{
    private static final AnarchiumClient INSTANCE = new AnarchiumClient();

    public static final Logger LOGGER = LogUtils.getLogger();

    public final EffectManager effectManager;

    public int timerTicks;

    public int timerDuration;

    private AnarchiumClient()
    {
        this.effectManager = new EffectManager();
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
        this.effectManager.tick(level, stage);
    }

    public static AnarchiumClient getInstance()
    {
        return INSTANCE;
    }
}
