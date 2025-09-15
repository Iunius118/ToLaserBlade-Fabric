package com.github.iunius118.tolaserblade.config;

public record TLBServerConfig (
        float laserBladeDamageModifier,
        float laserBladeSpeedModifier,
        int maxAttackDamageUpgradeCount,
        int attackDamageUpgradeMultiplier,
        boolean isLaserTrapEnabled,
        boolean canLaserTrapAttackPlayer,
        boolean canLaserTrapHeatUpFurnace
) {
    public static final TLBServerConfig DEFAULT = new TLBServerConfig(
            0.0F, 0.0F, 8, 1,
            true, true, true
    );
}
