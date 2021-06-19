package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeSerializers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class CommonRegister {
    public static void registerRecipeSerializers() {
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "color"), ModRecipeSerializers.COLOR);
    }

    public static void registerItems() {
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade"), ModItems.LASER_BLADE);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_fp"), ModItems.LASER_BLADE_FP);
    }

    private CommonRegister() {}
}
