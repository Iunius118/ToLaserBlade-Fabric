package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class LaserBladeModelLoadingPlugin implements ModelLoadingPlugin {
    @Override
    public void initialize(Context pluginContext) {
        // Load/Reload laser blade json models
        LaserBladeModelManager.getInstance().reload();
    }
}
