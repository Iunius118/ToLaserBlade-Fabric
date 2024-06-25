package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.integration.autoconfig.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class LaserBladeModelLoadingPlugin implements ModelLoadingPlugin {
    private static List<ModelResourceLocation> laserBladeModelLocations = Collections.emptyList();

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        // Load/Reload laser blade json models
        var modelManager = LaserBladeModelManager.getInstance();
        modelManager.reload();
        // Register UnbakedModels of laser blades
        laserBladeModelLocations = getLaserBladeModelLocations();
        pluginContext.modifyModelOnLoad().register(ModelModifier.OVERRIDE_PHASE, this::modifyModelOnLoad);
    }

    private List<ModelResourceLocation> getLaserBladeModelLocations() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        return getLaserBladeModelLocations(config.getLaserBladeItemIDs());
    }

    private List<ModelResourceLocation> getLaserBladeModelLocations(List<ResourceLocation> laserBladeItemIds) {
        return laserBladeItemIds.stream()
                .map(this::addItemPrefix)
                .map(ModelResourceLocation::inventory)
                .toList();
    }

    private ResourceLocation addItemPrefix(ResourceLocation id) {
        String path = id.getPath();

        if (!path.startsWith("item/")) {
            return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "item/" + path);
        }

        return id;
    }

    private UnbakedModel modifyModelOnLoad(UnbakedModel model, ModelModifier.OnLoad.Context context) {
        var location = ModelResourceLocation.inventory(context.resourceId());

        if (laserBladeModelLocations.contains(location)) {
            return new LBSwordItemModel();
        }

        return model;
    }
}
