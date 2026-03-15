package com.zickzenni.anarchium.mixin.server;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
    @Inject(at = @At("HEAD"), method = "getMaximumFlyingTicks(Lnet/minecraft/world/entity/Entity;)I", cancellable = true)
    public void getMaximumFlyingTicks(CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue(Integer.MAX_VALUE);
    }
}
