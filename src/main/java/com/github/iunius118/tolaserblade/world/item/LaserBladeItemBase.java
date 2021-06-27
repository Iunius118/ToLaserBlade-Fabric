package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;

public interface LaserBladeItemBase {
    static Item.Properties setFireResistant(Item.Properties properties, boolean isFireResistant) {
        return isFireResistant ? properties.fireResistant() : properties;
    }
}
