package com.github.iunius118.tolaserblade.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class LBSwordItemTier implements Tier {
    public static final int MAX_USES = 32000;
    public static final float DEFAULT_SPEED = 12.0F;
    public static final float NORMAL_DAMAGE = 3.0F;
    public static final float NETHERITIC_DAMAGE = 4.0F;
    public static final int HARVEST_LEVEL = 3;
    public static final int NETHERITIC_HARVEST_LEVEL = 4;
    public static final int ENCHANTMENT_VALUE = 22;

    private final boolean isNetheritic;

    public LBSwordItemTier(boolean isFireproof) {
        isNetheritic = isFireproof;
    }

    @Override
    public int getUses() {
        return MAX_USES;
    }

    @Override
    public float getSpeed() {
        return DEFAULT_SPEED;
    }

    @Override
    public float getAttackDamageBonus() {
        return isNetheritic ? NETHERITIC_DAMAGE : NORMAL_DAMAGE;
    }

    @Override
    public int getLevel() {
        return isNetheritic ? NETHERITIC_HARVEST_LEVEL : HARVEST_LEVEL;
    }

    @Override
    public int getEnchantmentValue() {
        return ENCHANTMENT_VALUE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }
}
