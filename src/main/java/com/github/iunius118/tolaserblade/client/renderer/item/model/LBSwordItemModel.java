package com.github.iunius118.tolaserblade.client.renderer.item.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedOverrides;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class LBSwordItemModel implements UnbakedModel, BakedModel {
    public ItemStack itemStack = ItemStack.EMPTY;
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
    public boolean isBlocking = false;
    private final BakedOverrides bakedOverrides = new LBSwordBakedOverrides(this);

    public LBSwordItemModel handleItemOverride(ItemStack itemStackIn, HumanoidArm mainArmIn, boolean isBlockingIn) {
        // Override model for rendering
        var newLBSwordModel = new LBSwordItemModel();
        newLBSwordModel.itemStack = itemStackIn;
        newLBSwordModel.mainArm = mainArmIn;
        newLBSwordModel.isBlocking = isBlockingIn;
        return newLBSwordModel;
    }

    /* UnbakedModel */

    @Override
    public void resolveDependencies(Resolver resolver) {

    }

    @Override
    public BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState) {
        return this;
    }

    /* BakedModel */

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getItemRenderer().getModel(new ItemStack(Items.IRON_INGOT), null, null, 0).getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        if (!isBlocking) return LBSwordItemTransforms.ITEM_TRANSFORMS.get();

        if (mainArm == HumanoidArm.RIGHT) {
            return LBSwordItemTransforms.BLOCKING_RIGHT_ITEM_TRANSFORMS.get();
        } else {
            return LBSwordItemTransforms.BLOCKING_LEFT_ITEM_TRANSFORMS.get();
        }
    }

    @Override
    public BakedOverrides overrides() {
        return bakedOverrides;
    }
}
