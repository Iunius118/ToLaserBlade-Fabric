package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;
import java.util.function.Supplier;

public class RemoveEfficiencyUpgrade extends Upgrade {
    public RemoveEfficiencyUpgrade(Supplier<Ingredient> ingredientSupplier, String shortName) {
        super(ingredientSupplier, shortName);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, base);
        return level > 0;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, base);

        if (level > 0) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(base);
            enchantments.remove(Enchantments.BLOCK_EFFICIENCY);
            EnchantmentHelper.setEnchantments(enchantments, base);
            cost += 1;
        }

        return UpgradeResult.of(base, cost);
    }
}
