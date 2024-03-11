package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;

public class LBBlueprintItem extends Item {
    private LBBlueprintItem() {
        super(new Item.Properties());
    }

    public static LBBlueprintItem getInstance() {
        var lbBlueprintItem = new LBBlueprintItem();
        // This item is not consumed by crafting
        lbBlueprintItem.craftingRemainingItem = lbBlueprintItem;
        return lbBlueprintItem;
    }
}
