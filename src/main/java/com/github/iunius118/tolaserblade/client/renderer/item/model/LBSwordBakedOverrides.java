package com.github.iunius118.tolaserblade.client.renderer.item.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LBSwordBakedOverrides extends BakedOverrides {
    private final LBSwordItemModel lbSwordItemModel;

    public LBSwordBakedOverrides(LBSwordItemModel lbSwordItemModel) {
        super(null, List.of());
        this.lbSwordItemModel = lbSwordItemModel;
    }

    @Nullable
    @Override
    public BakedModel findOverride(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        var humanoidArm = Minecraft.getInstance().options.mainHand().get();
        var isBlocking = false;

        if (livingEntity != null) {
            humanoidArm = livingEntity.getMainArm();
            isBlocking = livingEntity.isUsingItem();
        }

        return lbSwordItemModel.handleItemOverride(itemStack, humanoidArm, isBlocking);
    }
}
