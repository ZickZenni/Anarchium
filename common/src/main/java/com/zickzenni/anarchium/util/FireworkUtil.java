package com.zickzenni.anarchium.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;

import java.util.List;

public final class FireworkUtil
{
    private static final RandomSource RANDOM = RandomSource.create();

    private FireworkUtil()
    {
    }

    /**
     * Creates and returns a randomly generated Fireworks object. The firework has a random
     * duration, shape, primary colors, fade colors, and optional features such as trail
     * and twinkle effects.
     *
     * @return A Fireworks object with randomized properties.
     */
    public static Fireworks createRandomFirework()
    {
        var duration = RANDOM.nextIntBetweenInclusive(1, 3);
        var shape = Util.getRandom(FireworkExplosion.Shape.values(), RANDOM);

        var colors = new IntArrayList();
        var colorAmount = RANDOM.nextIntBetweenInclusive(1, 3);

        var fadeColors = new IntArrayList();
        var fadeColorAmount = RANDOM.nextIntBetweenInclusive(0, 2);

        for (var i = 0; i < colorAmount; i++)
        {
            colors.add(Util.getRandom(DyeColor.values(), RANDOM).getFireworkColor());
        }

        for (var i = 0; i < fadeColorAmount; i++)
        {
            fadeColors.add(Util.getRandom(DyeColor.values(), RANDOM).getFireworkColor());
        }

        var trail = RANDOM.nextInt() % 2 == 0;
        var twinkle = RANDOM.nextInt() % 2 == 0;

        return new Fireworks(
                (byte) duration,
                List.of(new FireworkExplosion(shape, colors, fadeColors, trail, twinkle))
        );
    }

    /**
     * Summons a firework rocket entity at the specified coordinates in the given level,
     * using the provided firework configuration.
     *
     * @param firework The firework configuration used to define the appearance and behavior of the firework.
     * @param level    The level in which the firework rocket entity will be spawned.
     * @param x        The x-coordinate where the firework rocket entity will be placed.
     * @param y        The y-coordinate where the firework rocket entity will be placed.
     * @param z        The z-coordinate where the firework rocket entity will be placed.
     * @return The spawned {@code FireworkRocketEntity} instance.
     */
    public static FireworkRocketEntity summonFirework(Fireworks firework, Level level, double x, double y, double z)
    {
        var itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        itemStack.set(DataComponents.FIREWORKS, firework);

        var entity = new FireworkRocketEntity(level, x, y, z, itemStack);
        level.addFreshEntity(entity);

        return entity;
    }
}
