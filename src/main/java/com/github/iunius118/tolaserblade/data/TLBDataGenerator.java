package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class TLBDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(TLBRecipeProvider::new);
        pack.addProvider(TLBEnchantmentProvider::new);
        pack.addProvider(TLBItemTagsProvider::new);
        pack.addProvider(TLBEnchantmentTagsProvider::new);
        pack.addProvider(TLBEntityTypeTagsProvider::new);
        TLBLanguageProvider.addProviders(pack);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.ENCHANTMENT, this::bootstrapEnchantmentDataRegistry);
    }

    private void bootstrapEnchantmentDataRegistry(BootstrapContext<Enchantment> bootstrapContext) {
        HolderGetter<Item> itemHolderGetter = bootstrapContext.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantmentHolderGetter = bootstrapContext.lookup(Registries.ENCHANTMENT);
        bootstrapContext.register(ModEnchantments.LIGHT_ELEMENT, LightElementEnchantment.get(itemHolderGetter, enchantmentHolderGetter));
    }
}
