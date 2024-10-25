package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;

public class LBBlueprintItem extends Item {
    public LBBlueprintItem(Properties properties) {
        super(properties);
        this.craftingRemainingItem = this;
    }
}
