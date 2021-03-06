package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class DamageUpgrade extends Upgrade {
    public DamageUpgrade(Supplier<Ingredient> ingredientSupplier, String shortName) {
        super(ingredientSupplier, shortName);
    }

    @Override
    public boolean test(ItemStack base, ItemStack addition) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = getMaxUpgradeCount();
        return attack.damage < maxUpgradeCount;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        int cost = baseCost;
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = getMaxUpgradeCount();

        if (attack.damage < maxUpgradeCount) {
            attack.changeDamageSafely(Math.min(attack.damage + 1.0F, maxUpgradeCount));
            laserBlade.write(base);
            cost += getCost(attack.damage);
        }

        return UpgradeResult.of(base, cost);
    }

    private float getMaxUpgradeCount() {
        // Immediate value instead of config
        // return (float)ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();
        return 8;
    }

    private int getCost(float newDamage) {
        return Math.max((int)newDamage, 1);
    }
}
