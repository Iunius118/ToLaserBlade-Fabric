package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class TLBEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public TLBEnchantmentTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.DAMAGE_EXCLUSIVE).add(ModEnchantments.LIGHT_ELEMENT);
        tag(EnchantmentTags.TOOLTIP_ORDER).add(ModEnchantments.LIGHT_ELEMENT);
    }
}
