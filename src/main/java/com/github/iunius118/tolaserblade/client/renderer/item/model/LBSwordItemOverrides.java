package com.github.iunius118.tolaserblade.client.renderer.item.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class LBSwordItemOverrides extends ItemOverrides {
    public LBSwordItemOverrides() {
        super(null, null, Collections.emptyList());
    }

    @Nullable
    @Override
    public BakedModel resolve(BakedModel bakedModel, ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        if (!(bakedModel instanceof LBSwordItemModel laserBladeModel)) return bakedModel;

        var humanoidArm = Minecraft.getInstance().options.mainHand().get();
        var isBlocking = false;

        if (livingEntity != null) {
            humanoidArm = livingEntity.getMainArm();
            isBlocking = livingEntity.isUsingItem();
        }

        return laserBladeModel.handleItemOverride(itemStack, humanoidArm, isBlocking);
    }
}
