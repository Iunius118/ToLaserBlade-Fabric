package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;

public class LaserBladeJsonModelLoader {
    public static LaserBladeModel parse(String name, String json) {
        return parseModel(name, json);
    }
    private static LaserBladeModel parseModel(String name, String json) {
        // TODO: Support multiple model formats if needed
        return LaserBladeModelV1.parseModel(name, json);
    }
}
