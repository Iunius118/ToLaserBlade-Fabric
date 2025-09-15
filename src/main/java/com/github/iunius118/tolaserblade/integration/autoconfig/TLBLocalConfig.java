package com.github.iunius118.tolaserblade.integration.autoconfig;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.Mth;

@Config(name = ToLaserBlade.MOD_ID)
public class TLBLocalConfig implements ConfigData {
    // The value below is for the config file and differs from the value used in-game
    float laserBladeBaseDamage = 6.0F;
    // The value below is for the config file and differs from the value used in-game
    float laserBladeBaseSpeed = 2.8F;
    int maxAttackDamageUpgradeCount = 8;
    int attackDamageUpgradeMultiplier = 1;
    boolean enableLaserTrap = true;
    boolean canLaserTrapAttackPlayer = true;
    boolean canLaserTrapHeatUpFurnace = true;

    @Override
    public void validatePostLoad() throws ValidationException {
        correctValues();
        // Update server config used in-game
        updateServerConfig();
        ToLaserBlade.LOGGER.info("Loaded local config: {}", ToLaserBlade.serverConfig);
    }

    private void correctValues() {
        laserBladeBaseDamage = Mth.clamp(laserBladeBaseDamage, 0.0F, 2046.0F);
        laserBladeBaseSpeed = Mth.clamp(laserBladeBaseSpeed, 0.8F, 2.8F);
        maxAttackDamageUpgradeCount = Mth.clamp(maxAttackDamageUpgradeCount, 0, 2047);
        attackDamageUpgradeMultiplier = Mth.clamp(attackDamageUpgradeMultiplier, 1, 2048);
    }

    public void updateServerConfig() {
        ToLaserBlade.serverConfig = new TLBServerConfig(
                calcAttackDamageModifier(laserBladeBaseDamage),
                calcAttackSpeedModifier(laserBladeBaseSpeed),
                maxAttackDamageUpgradeCount,
                attackDamageUpgradeMultiplier,
                enableLaserTrap,
                canLaserTrapAttackPlayer,
                canLaserTrapHeatUpFurnace
        );
    }

    private static float calcAttackDamageModifier(float f) {
        // Fix the base damage [0.0, 2046.0] to match the values used in-game [-6.0, 2040.0]
        // ((Entity) 1 + (Sword) 3 + (Laser Blade) 3 + (Netherite) [0, 1]) + ((config) f[0, 2046] - 6) = [1, 2048]
        return f - 6.0F;
    }

    private static float calcAttackSpeedModifier(float f) {
        // Fix the base speed [0.8, 2.8] to match the values used in-game [-2.0, 0.0]
        // ((Entity) 4 + (Laser Blade) -1.2) + ((config) f[0.8, 2.8] - 2.8) = [0.8, 2.8]
        return f - 2.8F;
    }
}
