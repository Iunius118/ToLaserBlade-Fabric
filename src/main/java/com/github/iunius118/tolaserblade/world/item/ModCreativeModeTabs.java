package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class ModCreativeModeTabs {
    private static final List<ItemStack> generalItems = Stream.of(
            ModItems.LASER_BLADE,
            ModItems.LASER_BLADE_FP,
            ModItems.LB_BLUEPRINT,
            ModItems.LB_BATTERY,
            ModItems.LB_MEDIUM,
            ModItems.LB_EMITTER,
            ModItems.LB_CASING
    ).map(ItemStack::new).toList();

    public static final CreativeModeTab TAB_LASER_BLADE = new FabricItemGroup(new ResourceLocation(ToLaserBlade.MOD_ID, "general")) {
        @Override
        public ItemStack makeIcon() {
            return LaserBladeItemStack.ICON.getCopy();
        }

        @Override
        protected void generateDisplayItems(FeatureFlagSet featureFlagSet, Output output, boolean bl) {
            output.acceptAll(generalItems);
        }
    };

    public static void initModCreativeModeTabs() {}
}
