package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

import java.util.Optional;

public class TLBSampleSoundPackProvider {
    public final static String PACK_PATH = "sample_sound_pack";
    public final static ResourceLocation PACK_ID = ToLaserBlade.makeId(PACK_PATH);
    public final static String PACK_TITLE = "TLB Sample Sound Pack";
    public final static String PACK_DESCRIPTION = "ToLaserBlade - sample sound pack for laser blade";

    private TLBSampleSoundPackProvider() {}

    public static void addProviders(final FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack builtinResourcePack = fabricDataGenerator.createBuiltinResourcePack(PACK_ID);

        var packMetadataSection = new PackMetadataSection(Component.literal(PACK_DESCRIPTION), DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
        var provider = (FabricDataGenerator.Pack.Factory<PackMetadataGenerator>) o -> new PackMetadataGenerator(o).add(PackMetadataSection.TYPE, packMetadataSection);
        builtinResourcePack.addProvider(provider);
    }

    public static void addResourcePack(ModContainer container) {
        ResourceManagerHelper.registerBuiltinResourcePack(PACK_ID, container, Component.literal(PACK_TITLE), ResourcePackActivationType.NORMAL);
    }
}
