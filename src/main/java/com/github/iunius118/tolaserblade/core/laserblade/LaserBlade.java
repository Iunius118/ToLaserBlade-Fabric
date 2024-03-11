package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import com.github.iunius118.tolaserblade.core.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class LaserBlade {
    public static final float MOD_ATK_MIN = 0.0F;
    public static final float MOD_ATK_MAX = 2040.0F;
    public static final float MOD_ATK_GIFT = 3.0F;
    public static final float MOD_ATK_CRITICAL_BONUS = 8.0F;
    public static final float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;
    public static final float MOD_SPD_MIN = 0.0F;
    public static final float MOD_SPD_MAX = 1.2F;
    public static final int TYPE_DEFAULT = 0;
    public static final String KEY_ATK = "ATK";
    public static final String KEY_SPD = "SPD";
    public static final String KEY_TYPE = "type";

    private final CompoundTag tag;

    public LaserBlade(ItemStack itemStack) {
        CustomData customData = itemStack.get(ModDataComponents.LASER_BLADE);
        
        if (customData == null) {
            customData = itemStack.get(DataComponents.CUSTOM_DATA);
        }
        
        if (customData == null) {
            tag = null;
        } else {
            // Read only, don't create any tag
            tag = customData.copyTag();
        }
    }

    public static LaserBlade of(ItemStack itemStack) {
        return new LaserBlade(itemStack);
    }

    public static boolean canUpgradeDamage(float damage) {
        return damage < getMaxUpgradeCount();
    }

    private static float getMaxUpgradeCount() {
        // Immediate value instead of config
        // return (float) ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();
        return 8;
    }

    public static boolean canUpgradeSpeed(float speed) {
        return speed < MOD_SPD_MAX;
    }

    public float getDamage() {
        if (tag != null) {
            return Mth.clamp(tag.getFloat(KEY_ATK), MOD_ATK_MIN, MOD_ATK_MAX);
        }

        return MOD_ATK_MIN;
    }

    public float getSpeed() {
        if (tag != null) {
            return Mth.clamp(tag.getFloat(KEY_SPD), MOD_SPD_MIN, MOD_SPD_MAX);
        }

        return MOD_SPD_MIN;
    }

    public int getType() {
        if (tag != null) {
            return Math.max(tag.getInt(KEY_TYPE), 0);
        }

        return TYPE_DEFAULT;
    }

    public LaserBladeVisual getVisual() {
        return new LaserBladeVisual(tag);
    }

    public LaserBladeState getState() {
        LaserBladeVisual visual = getVisual();
        LaserBladeState.Part inner = new LaserBladeState.Part(visual.innerColorExists(), visual.getInnerColor(), visual.isInnerSubColor());
        LaserBladeState.Part outer = new LaserBladeState.Part(visual.outerColorExists(), visual.getOuterColor(), visual.isOuterSubColor());
        LaserBladeState.Part grip = new LaserBladeState.Part(visual.gripColorExists(), visual.getGripColor(), false);

        return new LaserBladeState(getDamage(), getSpeed(), getType(), inner, outer, grip);
    }
}
