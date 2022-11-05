package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeJsonModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LaserBladeModelManager {
    private static final Logger LOGGER = LogManager.getFormatterLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelManager");
    private static LaserBladeModelManager instance;
    private final Map<Integer, LaserBladeModel> models;
    private LaserBladeModel defaultModel = null;

    public static LaserBladeModelManager renewInstance() {
        instance = new LaserBladeModelManager();
        return instance;
    }

    public static LaserBladeModelManager getInstance() {
        return instance != null ? instance : renewInstance();
    }

    private LaserBladeModelManager() {
        models = new HashMap<>();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        // Search json files from resource packs
        Map<ResourceLocation, Resource> resourceMap = resourceManager.listResources(
                "models/item/laser_blade", resourceLocation -> {
                    String namespace = resourceLocation.getNamespace();
                    String path = resourceLocation.getPath();
                    return namespace.equals(ToLaserBlade.MOD_ID) && path.endsWith(".json");
                }
        );

        // Parse and Register models
        resourceMap.forEach((location, resource) -> {
            LaserBladeModel model = LaserBladeJsonModelLoader.parse(location.toString(), resource);
            if (model != null) {models.put(model.getID(), model);}
        });

        defaultModel = models.get(0);

        int size = models.size();

        if (size == 1) {
            LOGGER.info("1 model has been loaded as a laser blade model");
        } else {
            LOGGER.info(size + " models have been loaded as laser blade models");
        }
    }

    public static LaserBladeModel getModel(int modelID) {
        var modelManager = getInstance();
        LaserBladeModel model = modelManager.models.get(modelID);

        if (model != null) {
            return model;
        } else {
            return modelManager.defaultModel;
        }
    }

}
