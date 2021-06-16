package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class CommonRegister {
    private CommonRegister() {}

    public static void registerItems() {
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade"), ModItems.LASER_BLADE);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_fp"), ModItems.LASER_BLADE_FP);
    }
}
