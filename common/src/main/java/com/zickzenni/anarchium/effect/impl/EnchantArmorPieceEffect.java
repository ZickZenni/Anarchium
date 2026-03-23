package com.zickzenni.anarchium.effect.impl;

import com.zickzenni.anarchium.effect.EffectProperties;
import com.zickzenni.anarchium.effect.base.InstantEffect;
import com.zickzenni.anarchium.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantArmorPieceEffect extends InstantEffect
{
    public static final EffectProperties<EnchantArmorPieceEffect> PROPERTIES =
            EffectProperties.Builder.of(EnchantArmorPieceEffect.class)
                    .id("enchant_armor_piece")
                    .supplier(EnchantArmorPieceEffect::new)
                    .build();

    public EnchantArmorPieceEffect()
    {
        super(PROPERTIES.getId());
    }

    @Override
    public void onStartServer()
    {
        for (var player : Services.PLAYER_PROVIDER.getServerPlayers())
        {
            List<ItemStack> items = new ArrayList<>();

            {
                var item = player.getItemBySlot(EquipmentSlot.HEAD);

                if (!item.isEmpty())
                {
                    items.add(item);
                }
            }

            {
                var item = player.getItemBySlot(EquipmentSlot.CHEST);

                if (!item.isEmpty())
                {
                    items.add(item);
                }
            }

            {
                var item = player.getItemBySlot(EquipmentSlot.LEGS);

                if (!item.isEmpty())
                {
                    items.add(item);
                }
            }

            {
                var item = player.getItemBySlot(EquipmentSlot.FEET);

                if (!item.isEmpty())
                {
                    items.add(item);
                }
            }

            if (items.isEmpty())
            {
                continue;
            }

            var item = items.get(player.level().random.nextInt(items.size()));

            if (!item.getItem().isEnchantable(item))
            {
                continue;
            }

            var registry = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var enchantments = registry.listElements().collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(enchantments);

//            for (var enchantment : enchantments)
//            {
//                if (!item.supportsEnchantment(enchantment))
//                {
//                    continue;
//                }
//
//                var itemEnchantmentLevel = item.getEnchantmentLevel(enchantment);
//
//                if (itemEnchantmentLevel < enchantment.value().getMaxLevel())
//                {
//                    item.enchant(enchantment, itemEnchantmentLevel + 1);
//                    break;
//                } else if (itemEnchantmentLevel == 0)
//                {
//                    var level = player.level().random.nextInt(enchantment.value().getMinLevel() + 1, enchantment.value()
//                            .getMaxLevel() + 1);
//                    item.enchant(enchantment, level);
//                    break;
//                }
//            }
        }
    }
}
