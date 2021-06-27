package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabs {
    public static final CreativeModeTab TAB_LASER_BLADE = FabricItemGroupBuilder
            .build(new ResourceLocation(ToLaserBlade.MOD_ID, "general"), LaserBladeItemStack.ICON.getSupplier());
}
