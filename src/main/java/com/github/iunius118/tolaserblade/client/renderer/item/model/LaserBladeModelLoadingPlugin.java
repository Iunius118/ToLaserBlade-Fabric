package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

public class LaserBladeModelLoadingPlugin implements ModelLoadingPlugin {
    public static final ResourceLocation LB_SWORD_MODEL = new ModelResourceLocation("tolaserblade", "laser_blade", "inventory");
    public static final ResourceLocation LB_SWORD_FP_MODEL = new ModelResourceLocation("tolaserblade", "laser_blade_fp",  "inventory");

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelOnLoad().register(ModelModifier.OVERRIDE_PHASE, this::modifyModelOnLoad);
    }

    private UnbakedModel modifyModelOnLoad(UnbakedModel model, ModelModifier.OnLoad.Context context) {
        ResourceLocation resourceId = context.id();

        if (resourceId.equals(LB_SWORD_MODEL)) {
            // Load/Reload laser blade json models
            var modelManager = LaserBladeModelManager.getInstance();
            modelManager.reload();

            return new LBSwordItemModel();
        } else if (resourceId.equals(LB_SWORD_FP_MODEL)) {
            return new LBSwordItemModel();
        }

        return model;
    }
}
