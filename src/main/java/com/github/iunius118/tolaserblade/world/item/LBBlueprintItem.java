package com.github.iunius118.tolaserblade.world.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.Item;

public class LBBlueprintItem extends Item {
    private LBBlueprintItem() {
        super(new FabricItemSettings());
    }

    public static LBBlueprintItem getInstance() {
        var lbBlueprintItem = new LBBlueprintItem();
        // This item is not consumed by crafting
        lbBlueprintItem.craftingRemainingItem = lbBlueprintItem;
        return lbBlueprintItem;
    }
}
