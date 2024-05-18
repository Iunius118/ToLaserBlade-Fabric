package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class TLBItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public TLBItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ModItemTags.ATTACK_SPEED_UPGRADE).forceAddTag(ConventionalItemTags.GOLD_INGOTS);
        getOrCreateTagBuilder(ModItemTags.EFFICIENCY_UPGRADE).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_REDSTONE);
        getOrCreateTagBuilder(ModItemTags.EFFICIENCY_REMOVER).forceAddTag(ConventionalItemTags.REDSTONE_DUSTS);
        getOrCreateTagBuilder(ModItemTags.ATTACK_DAMAGE_UPGRADE).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_DIAMOND);
        getOrCreateTagBuilder(ModItemTags.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        getOrCreateTagBuilder(ModItemTags.FIRE_ASPECT_UPGRADE).forceAddTag(ConventionalItemTags.BLAZE_RODS);
        getOrCreateTagBuilder(ModItemTags.SWEEPING_EDGE_UPGRADE).add(Items.ENDER_EYE);
        getOrCreateTagBuilder(ModItemTags.SILK_TOUCH_UPGRADE).forceAddTag(ConventionalItemTags.PRISMARINE_GEMS).forceAddTag(ConventionalItemTags.AMETHYST_GEMS).add(Items.ECHO_SHARD);
        getOrCreateTagBuilder(ModItemTags.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).forceAddTag(ConventionalItemTags.STORAGE_BLOCKS_EMERALD);
        getOrCreateTagBuilder(ModItemTags.MENDING_UPGRADE).add(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        getOrCreateTagBuilder(ModItemTags.CASING_REPAIR).forceAddTag(ConventionalItemTags.IRON_INGOTS);
    }
}