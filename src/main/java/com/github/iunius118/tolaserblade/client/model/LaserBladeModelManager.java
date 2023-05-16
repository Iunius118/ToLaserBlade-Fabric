package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.event.ToLaserBladeClientEvent;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeJsonModelLoader;
import com.github.iunius118.tolaserblade.client.model.laserblade.v1.LaserBladeModelV1;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LaserBladeModelManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelManager");
    private static final String MODEL_DIR = "models/item/laser_blade";
    private static final LaserBladeModelManager INSTANCE = new LaserBladeModelManager();

    private Map<Integer, LaserBladeModel> models = Collections.emptyMap();
    private LaserBladeModel defaultModel;

    private LaserBladeModelManager() {}

    public static LaserBladeModelManager getInstance() {
        return INSTANCE;
    }

    public static List<LaserBladeModel> loadModels() {
        return parseJsonModels();
    }

    public void reload() {
        // Clear model cache
        models = new HashMap<>();

        // Fire RegisterModel event to add models
        ToLaserBladeClientEvent.REGISTER_MODEL.invoker().registerModels(models -> models.forEach(this::addModel));

        // Set default model
        defaultModel = models.get(0);

        // Reset render types
        LaserBladeModelV1.resetRenderTypes();
    }

    public void logLoadedModelCount() {
        int count = models.size();

        if (count == 1) {
            LOGGER.info("1 model has been loaded as a laser blade model");
        } else {
            LOGGER.info("{} models have been loaded as laser blade models", count);
        }
    }

    private static List<LaserBladeModel> parseJsonModels() {
        Map<ResourceLocation, Resource> resourceMap = findJsonFiles();
        List<LaserBladeModel> jsonModels = new LinkedList<>();

        resourceMap.forEach((location, resource) -> {
            LaserBladeModel model = LaserBladeJsonModelLoader.parse(location.toString(), resource);
            if (model != null) {
                jsonModels.add(model);
            }
        });

        return jsonModels;
    }

    private static Map<ResourceLocation, Resource> findJsonFiles() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        // Search resource packs for .json files
        return resourceManager.listResources(
                MODEL_DIR, resourceLocation -> {
                    String namespace = resourceLocation.getNamespace();
                    String path = resourceLocation.getPath();
                    return namespace.equals(ToLaserBlade.MOD_ID) && path.endsWith(".json");
                }
        );
    }

    public void addModel(LaserBladeModel model) {
        if (model == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add null as Laser Blade model.");
            return;
        }

        int index = model.getID();

        if (index < 0) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add a model to invalid index {}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("[ToLaserBlade] Laser Blade model #{} already exists. It will be overwritten.", index);
        }

        models.put(index, model);
    }

    public LaserBladeModel getModel() {
        return defaultModel;
    }

    public LaserBladeModel getModel(ItemStack itemStack) {
        int modelType = LaserBlade.visualOf(itemStack).getModelType();
        LaserBladeModel model = getModel(modelType);

        if (model != null) {
            return model;
        } else {
            return defaultModel;
        }
    }

    public LaserBladeModel getModel(int modelID) {
        LaserBladeModel model = models.get(modelID);

        if (model != null) {
            return model;
        } else {
            return defaultModel;
        }
    }

    public Map<Integer, LaserBladeModel> getModels() {
        return models;
    }
}
