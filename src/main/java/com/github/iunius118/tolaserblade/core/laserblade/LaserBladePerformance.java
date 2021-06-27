package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class LaserBladePerformance {
    private final AttackPerformance attackPerformance;
    private final boolean isFireResistant;

    public LaserBladePerformance(CompoundTag compoundTag, boolean isFireResistantIn) {
        attackPerformance = new AttackPerformance(compoundTag);
        isFireResistant = isFireResistantIn;
    }

    public AttackPerformance getAttackPerformance() {
        return attackPerformance;
    }

    public boolean isFireResistant() {
        return isFireResistant;
    }

    public void write(CompoundTag compoundTag) {
        attackPerformance.write(compoundTag);
    }

    /* Inner classes */

    public static class AttackPerformance {
        public float damage;
        public float speed;

        public static final float MOD_ATK_MIN = 0.0F;
        public static final float MOD_ATK_GIFT = 3.0F;
        public static final float MOD_ATK_CRITICAL_BONUS = 8.0F;
        public static final float MOD_ATK_MAX = 2040.0F;
        public static final float MOD_SPD_MIN = 0.0F;
        public static final float MOD_SPD_MAX = 1.2F;

        public static final float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;

        public static final String KEY_ATK = "ATK";
        public static final String KEY_SPD = "SPD";

        public AttackPerformance(CompoundTag compoundTag) {
            damage = Mth.clamp(compoundTag.getFloat(KEY_ATK), MOD_ATK_MIN, MOD_ATK_MAX);
            speed = Mth.clamp(compoundTag.getFloat(KEY_SPD), MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public void changeDamageSafely(float damage) {
            this.damage = Mth.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX);
        }

        public void changeSpeedSafely(float speed) {
            this.speed = Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX);
        }

        public boolean canUpgradeSpeed() {
            return this.speed < MOD_SPD_MAX;
        }

        public void write(CompoundTag compoundTag) {
            compoundTag.putFloat(KEY_ATK, Mth.clamp(damage, MOD_ATK_MIN, MOD_ATK_MAX));
            compoundTag.putFloat(KEY_SPD, Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX));
        }
    }
}
