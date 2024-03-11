package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class EnchantmentUpgrader implements Upgrader {
    private final Enchantment enchantment;

    public EnchantmentUpgrader(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public static EnchantmentUpgrader of(Enchantment enchantment) {
        return new EnchantmentUpgrader(enchantment);
    }

    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, base);
        return level < enchantment.getMaxLevel();
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, base);

        if (level < enchantment.getMaxLevel()) {
            EnchantmentHelper.updateEnchantments(base, m -> {
                // Remove not compatible enchantments
                m.removeIf(e -> !isCompatibleWith(e.value(), enchantment));
                // Add enchantment or increase enchantment's level
                m.set(enchantment, level + 1);
            });
            cost += getCost(level);
        }

        return UpgradeResult.of(base, cost);
    }

    private boolean isCompatibleWith(Enchantment e1, Enchantment e2) {
        return e1.isCompatibleWith(e2) ||
                (e1 == Enchantments.SILK_TOUCH && e2 == Enchantments.LOOTING) || (e1 == Enchantments.LOOTING && e2 == Enchantments.SILK_TOUCH);
                // Allow Laser Blade to have Silk Touch and Looting together
    }

    private int getCost(int newLevel) {
        Enchantment.Rarity rarity = enchantment.getRarity();
        int rate = (!(rarity == Enchantment.Rarity.RARE || rarity == Enchantment.Rarity.VERY_RARE)) ? 1
                : (rarity == Enchantment.Rarity.RARE) ? 2 : 4;  // Half rate (same as enchanted book)
        return Math.max(rate * newLevel, 1);
    }
}
