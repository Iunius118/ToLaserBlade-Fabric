package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import net.minecraft.world.item.ItemStack;

public class DamageUpgrader implements Upgrader {
    @Override
    public boolean canApply(ItemStack base, ItemStack addition) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = getMaxUpgradeCount();
        return attack.damage < maxUpgradeCount;
    }

    @Override
    public UpgradeResult apply(ItemStack base, int baseCost) {
        final LaserBlade laserBlade = LaserBlade.of(base);
        final LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
        float maxUpgradeCount = getMaxUpgradeCount();
        int cost = baseCost;

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
