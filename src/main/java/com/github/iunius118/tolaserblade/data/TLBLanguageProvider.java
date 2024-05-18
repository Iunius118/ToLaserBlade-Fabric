package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.EnglishTranslationProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TLBLanguageProvider {
    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(EnglishTranslationProvider::new);
    }
}
