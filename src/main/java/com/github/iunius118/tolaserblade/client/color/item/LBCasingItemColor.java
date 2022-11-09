package com.github.iunius118.tolaserblade.client.color.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class LBCasingItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            LaserBladeItemColor color = LaserBladeItemColor.of(stack);
            return color.gripColor | 0xFF000000;
        }

        return 0xFFFFFFFF;
    }
}
