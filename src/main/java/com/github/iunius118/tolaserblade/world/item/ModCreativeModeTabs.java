package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    public static final CreativeModeTab TAB_LASER_BLADE = FabricItemGroup.builder()
            .title(LaserBladeTextKey.KEY_ITEM_GROUP_GENERAL.translate())
            .icon(LaserBladeItemStack.ICON::getCopy)
            .displayItems((params, output) -> output.acceptAll(generalItems))
            .build();

    private static void modifyEntries(FabricItemGroupEntries content) {
        // Register model-changed laser blades to mod creative mode tab
        List<Integer> modelTypes = LaserBladeModelManager.getInstance().getModels().keySet().stream().sorted().toList();
        for (int modelType : modelTypes) {
            if (modelType != 0) {
                ItemStack laserBlade = LaserBladeItemStack.getModelChangedStack(modelType, false);
                content.accept(laserBlade);
            }
        }
    }

    public static void initModCreativeModeTabs() {
        ResourceKey<CreativeModeTab> resourceKey = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), new ResourceLocation(ToLaserBlade.MOD_ID, "general"));
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceKey, TAB_LASER_BLADE);
        ItemGroupEvents.modifyEntriesEvent(resourceKey).register(ModCreativeModeTabs::modifyEntries);
    }
}
