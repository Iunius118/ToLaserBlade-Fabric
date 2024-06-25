package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class TLBEnchantmentTagsProvider extends FabricTagProvider.EnchantmentTagProvider {
    public TLBEnchantmentTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(EnchantmentTags.DAMAGE_EXCLUSIVE).addOptional(ModEnchantments.LIGHT_ELEMENT);
        getOrCreateTagBuilder(EnchantmentTags.TOOLTIP_ORDER).addOptional(ModEnchantments.LIGHT_ELEMENT);
    }
}
