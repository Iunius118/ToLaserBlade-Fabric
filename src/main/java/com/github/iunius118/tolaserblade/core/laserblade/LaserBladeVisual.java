package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.core.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class LaserBladeVisual {
    public static final int MODEL_TYPE_DEFAULT = LaserBlade.TYPE_DEFAULT;
    public static final String KEY_MODEL_TYPE = LaserBlade.KEY_TYPE;
    public static final String KEY_INNER_COLOR = "colorC";
    public static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
    public static final String KEY_OUTER_COLOR = "colorH";
    public static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";
    public static final String KEY_GRIP_COLOR = "colorG";

    private final CompoundTag tag;

    public LaserBladeVisual(ItemStack itemStack) {
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

    public LaserBladeVisual(CompoundTag compoundTag) {
        tag = compoundTag;
    }

    public static LaserBladeVisual of(ItemStack itemStack) {
        return new LaserBladeVisual(itemStack);
    }

    public int getModelType() {
        if (tag != null) {
            return Math.max(tag.getInt(KEY_MODEL_TYPE), 0);
        }

        return MODEL_TYPE_DEFAULT;
    }

    public boolean innerColorExists() {
        return tag != null && tag.contains(KEY_INNER_COLOR, Tag.TAG_INT);
    }

    public int getInnerColor() {
        if (innerColorExists()) {
            return tag.getInt(KEY_INNER_COLOR);
        }

        return LaserBladeColor.WHITE.getBladeColor();
    }

    public boolean isInnerSubColor() {
        if (tag != null) {
            return tag.getBoolean(KEY_IS_INNER_SUB_COLOR);
        }

        return false;
    }

    public boolean outerColorExists() {
        return tag != null && tag.contains(KEY_OUTER_COLOR, Tag.TAG_INT);
    }

    public int getOuterColor() {
        if (outerColorExists()) {
            return tag.getInt(KEY_OUTER_COLOR);
        }

        return LaserBladeColor.RED.getBladeColor();
    }

    public boolean isOuterSubColor() {
        if (tag != null) {
            return tag.getBoolean(KEY_IS_OUTER_SUB_COLOR);
        }

        return false;
    }

    public boolean gripColorExists() {
        return tag != null && tag.contains(KEY_GRIP_COLOR, Tag.TAG_INT);
    }

    public int getGripColor() {
        if (gripColorExists()) {
            return tag.getInt(KEY_GRIP_COLOR);
        }

        return LaserBladeColor.WHITE.getGripColor();
    }
}
