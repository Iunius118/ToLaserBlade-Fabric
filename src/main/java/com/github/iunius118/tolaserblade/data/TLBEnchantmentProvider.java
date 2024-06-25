package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TLBEnchantmentProvider extends FabricCodecDataProvider<Enchantment> {
    protected TLBEnchantmentProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "enchantment", Enchantment.DIRECT_CODEC);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, Enchantment> provider, HolderLookup.Provider lookup) {
        provider.accept(LightElementEnchantment.ID, LightElementEnchantment.get(lookup));
    }

    @Override
    public String getName() {
        return "Enchantment Definitions";
    }
}
