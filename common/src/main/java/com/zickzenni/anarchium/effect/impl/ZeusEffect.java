package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.event.ILevelTickEvent;
import com.zickzenni.anarchium.platform.Services;
import com.zickzenni.anarchium.registry.SoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

public class ZeusEffect extends InstantEffect
{
    public static final EffectProperties<ZeusEffect> PROPERTIES =
            EffectProperties.Builder.of(ZeusEffect.class).id("zeus").supplier(ZeusEffect::new).build();

    private boolean struck;
    private int ticks;

    public ZeusEffect()
    {
        super(PROPERTIES.getId());
        this.ticks = 80;
    }

    @Override
    public void onEndServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            EntityType.LIGHTNING_BOLT.spawn(player.serverLevel(), player.blockPosition(), MobSpawnType.MOB_SUMMONED);
        }
    }

    @Override
    public void onLevelTickServer(ILevelTickEvent<ServerLevel> event)
    {
        if (event.getStage() == ILevelTickEvent.Stage.POST)
        {
            if (this.ticks > 0)
            {
                this.ticks--;
            } else
            {
                this.onEndServer();
                this.struck = true;
            }
        }

        super.onLevelTickServer(event);
    }

    @Override
    public void onEndClient()
    {
        this.struck = true;
        super.onEndClient();
    }

    @Override
    public boolean hasEnded()
    {
        return this.struck;
    }

    @Override
    public String getGUIName()
    {
        if (!this.struck)
        {
            return "";
        }

        return super.getGUIName();
    }

    @Override
    public Holder<SoundEvent> getDispatchSound()
    {
        return SoundRegistry.ZEUS_EFFECT_SOUND;
    }
}
