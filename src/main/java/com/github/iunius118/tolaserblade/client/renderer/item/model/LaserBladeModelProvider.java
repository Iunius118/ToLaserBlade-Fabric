package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class LaserBladeModelProvider implements ModelResourceProvider {
    public static final ResourceLocation LB_SWORD_MODEL = new ResourceLocation("tolaserblade:item/laser_blade");
    public static final ResourceLocation LB_SWORD_FP_MODEL = new ResourceLocation("tolaserblade:item/laser_blade_fp");

    @Override
    @Nullable
    public UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) throws ModelProviderException {
        if (resourceId.equals(LB_SWORD_MODEL)) {
            // Load/Reload laser blade json models
            var modelManager = LaserBladeModelManager.getInstance();
            modelManager.reload();

            return new LBSwordItemModel();
        } else if (resourceId.equals(LB_SWORD_FP_MODEL)) {
            return new LBSwordItemModel();
        }

        return null;
    }
}
