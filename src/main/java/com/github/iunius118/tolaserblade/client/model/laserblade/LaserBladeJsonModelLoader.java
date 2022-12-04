package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.minecraft.server.packs.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class LaserBladeJsonModelLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeJsonModelLoader");

    public static LaserBladeModel parse(String name, Resource resource) {
        LaserBladeModel model = null;

        try {
            BufferedReader reader = resource.openAsReader();
            model = parseModel(name, reader);
            reader.close();
        } catch (IOException e) {
            LOGGER.warn("Failed to load json model: {}\n{}", name, e);
        }

        return model;
    }

    private static LaserBladeModel parseModel(String name, Reader reader) {
        // TODO: Support multiple model formats if needed
        return LaserBladeModelV1.parseModel(name, reader);
    }
}
