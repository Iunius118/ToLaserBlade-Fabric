package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class TLBEntityTypeTagsProvider extends FabricTagProvider.EntityTypeTagProvider {
    public TLBEntityTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(ModEntityTypeTags.SENSITIVE_TO_LIGHT_ELEMENT).forceAddTag(EntityTypeTags.UNDEAD).forceAddTag(EntityTypeTags.ILLAGER);
    }
}
