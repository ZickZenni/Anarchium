package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class EnchantCurrentItemEffect extends InstantEffect
{
    public static final EffectProperties<EnchantCurrentItemEffect> PROPERTIES =
            EffectProperties.Builder.of(EnchantCurrentItemEffect.class)
                    .id("enchant_current_item")
                    .supplier(EnchantCurrentItemEffect::new)
                    .build();

    public EnchantCurrentItemEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            var mainItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            var offItem = player.getItemInHand(InteractionHand.OFF_HAND);

            if (mainItem.isEmpty() && offItem.isEmpty())
            {
                continue;
            }

            var registry = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var enchantments = registry.listElements().collect(Collectors.toCollection(ArrayList::new));

            enchant(player, mainItem, enchantments);
            enchant(player, offItem, enchantments);
        }
    }

    private static void enchant(ServerPlayer player,
                                ItemStack stack,
                                ArrayList<Holder.Reference<Enchantment>> enchantments)
    {
        if (stack.isEmpty() || !stack.getItem().isEnchantable(stack))
        {
            return;
        }

        Collections.shuffle(enchantments);

//        for (var enchantment : enchantments)
//        {
//            if (!stack.supportsEnchantment(enchantment))
//            {
//                continue;
//            }
//
//            var itemEnchantmentLevel = stack.getEnchantmentLevel(enchantment);
//
//            if (itemEnchantmentLevel < enchantment.value().getMaxLevel())
//            {
//                stack.enchant(enchantment, itemEnchantmentLevel + 1);
//                break;
//            } else if (itemEnchantmentLevel == 0)
//            {
//                var level = player.level().random.nextInt(enchantment.value().getMinLevel() + 1, enchantment.value()
//                        .getMaxLevel() + 1);
//                stack.enchant(enchantment, level);
//                break;
//            }
//        }
    }
}
