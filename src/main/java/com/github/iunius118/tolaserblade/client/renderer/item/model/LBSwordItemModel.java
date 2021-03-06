package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
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
    public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        return Collections.emptySet();
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        return this;
    }

    /* BakedModel */

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random) {
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
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getParticleIcon(Items.IRON_INGOT);
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
