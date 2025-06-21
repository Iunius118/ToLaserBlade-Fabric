package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class TLBItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public TLBItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP);

        valueLookupBuilder(ModItemTags.ATTACK_SPEED_UPGRADE).forceAddTag(ConventionalItemTags.GOLD_INGOTS);
        valueLookupBuilder(ModItemTags.EFFICIENCY_UPGRADE).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_REDSTONE);
        valueLookupBuilder(ModItemTags.EFFICIENCY_REMOVER).forceAddTag(ConventionalItemTags.REDSTONE_DUSTS);
        valueLookupBuilder(ModItemTags.ATTACK_DAMAGE_UPGRADE).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_DIAMOND);
        valueLookupBuilder(ModItemTags.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        valueLookupBuilder(ModItemTags.FIRE_ASPECT_UPGRADE).forceAddTag(ConventionalItemTags.BLAZE_RODS);
        valueLookupBuilder(ModItemTags.SWEEPING_EDGE_UPGRADE).add(Items.ENDER_EYE);
        valueLookupBuilder(ModItemTags.SILK_TOUCH_UPGRADE).forceAddTag(ConventionalItemTags.PRISMARINE_GEMS).forceAddTag(ConventionalItemTags.AMETHYST_GEMS).add(Items.ECHO_SHARD);
        valueLookupBuilder(ModItemTags.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_EMERALD);
        valueLookupBuilder(ModItemTags.MENDING_UPGRADE).add(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        valueLookupBuilder(ModItemTags.CASING_REPAIR).forceAddTag(ConventionalItemTags.IRON_INGOTS);
        valueLookupBuilder(ModItemTags.ENCHANTABLE_LIGHT_ELEMENT).add(ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP, ModItems.LB_MEDIUM);
    }
}