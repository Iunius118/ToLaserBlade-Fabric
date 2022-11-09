package com.github.iunius118.tolaserblade.client.color.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class LBSwordItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        var laserBladeColor = LaserBladeItemColor.of(itemStack);

        return switch (tintIndex) {
            case 0 -> laserBladeColor.gripColor | 0xFF000000;
            case 1 -> laserBladeColor.simpleOuterColor;
            case 2 -> laserBladeColor.simpleInnerColor;
            default -> 0xFFFFFFFF;
        };
    }
}
