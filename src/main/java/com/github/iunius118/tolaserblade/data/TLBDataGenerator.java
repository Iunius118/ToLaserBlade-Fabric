package com.github.iunius118.tolaserblade.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TLBDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(TLBRecipeProvider::new);
        pack.addProvider(TLBItemTagsProvider::new);
        pack.addProvider(TLBEntityTypeTagsProvider::new);
        TLBLanguageProvider.addProviders(pack);
    }
}
