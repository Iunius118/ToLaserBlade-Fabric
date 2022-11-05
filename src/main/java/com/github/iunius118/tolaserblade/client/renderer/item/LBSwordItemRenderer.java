package com.github.iunius118.tolaserblade.client.renderer.item;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class LBSwordItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        int modelType = LaserBlade.visualOf(stack).getModelType();
        LaserBladeModel model = LaserBladeModelManager.getModel(modelType);

        if (model == null)
            return;

        matrices.pushPose();
        model.render(stack, mode, matrices, vertexConsumers, light, overlay);
        matrices.popPose();
    }
}
