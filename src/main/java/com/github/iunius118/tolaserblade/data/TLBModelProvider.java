package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LBSwordItemTransforms;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.Blocking;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.mixin.client.ItemInfoCollectorInvoker;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.MainHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class TLBModelProvider extends FabricModelProvider {
    public TLBModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        final var itemModelOutput = itemModelGenerator.itemModelOutput;
        final var itemInfoCollector = (ItemInfoCollectorInvoker) itemModelOutput;
        final var modelOutput = itemModelGenerator.modelOutput;

        // Laser Blades
        ItemModel.Unbaked lbSwordModel = generateLBSwordModel(ModItems.LASER_BLADE, modelOutput);
        itemInfoCollector.invokeRegister(ModItems.LASER_BLADE, new ClientItem(lbSwordModel, new ClientItem.Properties(true, true)));
        itemInfoCollector.invokeRegister(ModItems.LASER_BLADE_FP, new ClientItem(lbSwordModel, new ClientItem.Properties(true, true)));

        // Laser Blade Parts
        itemModelOutput.accept(ModItems.LB_BLUEPRINT,
                ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_BLUEPRINT),
                        TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_blueprint")), modelOutput)));

        itemModelOutput.accept(ModItems.LB_BATTERY,
                ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_BATTERY),
                        TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_battery")), modelOutput)));

        itemModelOutput.accept(ModItems.LB_MEDIUM,
                ItemModelUtils.tintedModel(itemModelGenerator.generateLayeredItem(ModItems.LB_MEDIUM,
                                ToLaserBlade.makeId("item/parts/lb_medium_0"), ToLaserBlade.makeId("item/parts/lb_medium_1")),
                        new Constant(0xFFFFFF), new LaserBladeTintSource(LaserBladeColorPart.OUTER_BLADE)));

        itemModelOutput.accept(ModItems.LB_EMITTER,
                ItemModelUtils.tintedModel(itemModelGenerator.generateLayeredItem(ModItems.LB_EMITTER,
                                ToLaserBlade.makeId("item/parts/lb_emitter_0"), ToLaserBlade.makeId("item/parts/lb_emitter_1")),
                        new Constant(0xFFFFFF), new LaserBladeTintSource(LaserBladeColorPart.INNER_BLADE)));

        itemModelOutput.accept(ModItems.LB_CASING,
                ItemModelUtils.tintedModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.LB_CASING),
                                TextureMapping.layer0(ToLaserBlade.makeId("item/parts/lb_casing")), modelOutput),
                        new LaserBladeTintSource(LaserBladeColorPart.GRIP)));
    }

    private ItemModel.Unbaked generateLBSwordModel(Item item, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        ItemModel.Unbaked blockingLeft = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, "_blocking_left"),
                LBSwordItemTransforms.BLOCKING_LEFT_ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());
        ItemModel.Unbaked blockingRight = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, "_blocking_right"),
                LBSwordItemTransforms.BLOCKING_RIGHT_ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());

        ItemModel.Unbaked blockingModel = ItemModelUtils.select(new MainHand(), blockingRight, ItemModelUtils.when(HumanoidArm.LEFT, blockingLeft));
        ItemModel.Unbaked defaultModel = ItemModelUtils.specialModel(createLBSwordModel(ModelLocationUtils.getModelLocation(item, ""),
                LBSwordItemTransforms.ITEM_TRANSFORMS, modelOutput), new LBSwordSpecialRenderer.Unbaked());

        return ItemModelUtils.conditional(new Blocking(), blockingModel, defaultModel);
    }

    private ResourceLocation createLBSwordModel(ResourceLocation location, LBSwordItemTransforms transforms, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        modelOutput.accept(location, () -> (new Gson()).toJsonTree(new LBSwordModel(ResourceLocation.withDefaultNamespace("item/iron_ingot"), transforms.get())));
        return location;
    }

    public static class LBSwordModel {
        @SerializedName("gui_light")
        public String guiLight = "front";
        public Map<String, String> textures = new HashMap<>();
        public Map<String, Map<String, float[]>> display = new HashMap<>();

        public LBSwordModel(ResourceLocation particle, ItemTransforms itemTransforms) {
            textures.put("particle", particle.toString());
            Arrays.stream(ItemDisplayContext.values())
                    .forEach(c -> addTransform(c.getSerializedName(), itemTransforms.getTransform(c)));
        }

        private void addTransform(String displayContext, ItemTransform itemTransform) {
            HashMap<String, float[]> transform = new HashMap<>();

            if (!ItemTransform.NO_TRANSFORM.scale().equals(itemTransform.scale())) {
                Vector3fc v = itemTransform.scale();
                transform.put("scale", new float[]{v.x(), v.y(), v.z()});
            }

            if (!ItemTransform.NO_TRANSFORM.rotation().equals(itemTransform.rotation())) {
                Vector3fc v = itemTransform.rotation();
                transform.put("rotation", new float[]{v.x(), v.y(), v.z()});
            }

            if (!ItemTransform.NO_TRANSFORM.translation().equals(itemTransform.translation())) {
                Vector3fc v = itemTransform.translation();
                transform.put("translation", new float[]{v.x() * 16F, v.y() * 16F, v.z() * 16F});
            }

            if (!transform.isEmpty()) {
                display.put(displayContext, transform);
            }
        }
    }
}
