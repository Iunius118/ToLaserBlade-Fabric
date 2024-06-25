package com.github.iunius118.tolaserblade.client.renderer.item.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LBSwordItemModel implements UnbakedModel, BakedModel {
    public ItemStack itemStack = ItemStack.EMPTY;
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
    public boolean isBlocking = false;

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
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> function) {
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState) {
        return this;
    }

    /* BakedModel */

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return Collections.emptyList();
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
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_INGOT).getParticleIcon();
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

    private static final ItemOverrides ITEM_OVERRIDES = new LBSwordItemOverrides();

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERRIDES;
    }
}
