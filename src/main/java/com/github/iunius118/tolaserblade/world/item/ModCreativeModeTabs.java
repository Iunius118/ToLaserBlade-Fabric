package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Stream;

public class ModCreativeModeTabs {
    public static final CreativeModeTab TAB_LASER_BLADE = new FabricItemGroup(new ResourceLocation(ToLaserBlade.MOD_ID, "general")) {
        @Override
        public ItemStack makeIcon() {
            return LaserBladeItemStack.ICON.getCopy();
        }

        @Override
        protected void generateDisplayItems(FeatureFlagSet featureFlagSet, Output output) {
            output.acceptAll(
                    Stream.of(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP)
                            .map(ItemStack::new)
                            .toList()
            );
        }
    };

    public static void initModCreativeModeTabs(){}
}
