package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.data.recipes.LBColorRecipeBuilder;
import com.github.iunius118.tolaserblade.data.recipes.LBModelChangeRecipeBuilder;
import com.github.iunius118.tolaserblade.data.recipes.LBUpgradeRecipeBuilder;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TLBRecipeProvider extends RecipeProvider {
    public TLBRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
        super(registryLookup, output);
    }

    @Override
    public void buildRecipes() {
        buildCraftingRecipes();
        buildSmithingRecipes();
    }

    private void buildCraftingRecipes() {
        final HolderLookup.RegistryLookup<Item> holderGetter = registries.lookupOrThrow(Registries.ITEM);

        // Laser Blade Blueprint
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_BLUEPRINT)
                .group(getItemId(ModItems.LB_BLUEPRINT))
                .pattern("rbr")
                .pattern("sps")
                .define('r', ConventionalItemTags.REDSTONE_DUSTS)
                .define('b', ConventionalItemTags.BLUE_DYES)
                .define('s', ConventionalItemTags.WOODEN_RODS)
                .define('p', Items.PAPER)
                .unlockedBy("has_redstone", has(ConventionalItemTags.REDSTONE_DUSTS))
                .save(output);

        // LB Energy Cell
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_BATTERY)
                .group(getItemId(ModItems.LB_BATTERY))
                .pattern("#")
                .pattern("i")
                .pattern("r")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', ConventionalItemTags.IRON_INGOTS)
                .define('r', ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Medium
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_MEDIUM)
                .group(getItemId(ModItems.LB_MEDIUM))
                .pattern("#")
                .pattern("g")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('g', ConventionalItemTags.GLOWSTONE_DUSTS)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_MEDIUM)
                .group(getItemId(ModItems.LB_MEDIUM))
                .pattern("#")
                .pattern("d")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('d', ConventionalItemTags.DIAMOND_GEMS)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output, getItemId(ModItems.LB_MEDIUM) + "_2");

        // Laser Blade Emitter
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_EMITTER)
                .group(getItemId(ModItems.LB_EMITTER))
                .pattern("#")
                .pattern("d")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('d', ConventionalItemTags.DIAMOND_GEMS)
                .define('i', ConventionalItemTags.IRON_INGOTS)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Blade Casing
        ShapedRecipeBuilder.shaped(holderGetter, RecipeCategory.MISC, ModItems.LB_CASING)
                .group(getItemId(ModItems.LB_CASING))
                .pattern("#")
                .pattern("i")
                .pattern("i")
                .define('#', ModItems.LB_BLUEPRINT)
                .define('i', ConventionalItemTags.IRON_INGOTS)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);

        // Laser Blade
        ShapelessRecipeBuilder.shapeless(holderGetter, RecipeCategory.TOOLS, ModItems.LASER_BLADE)
                .group(getItemId(ModItems.LASER_BLADE))
                .requires(ModItems.LB_BLUEPRINT)
                .requires(ModItems.LB_BATTERY)
                .requires(ModItems.LB_MEDIUM)
                .requires(ModItems.LB_EMITTER)
                .requires(ModItems.LB_CASING)
                .unlockedBy("has_lb_blueprint", has(ModItems.LB_BLUEPRINT))
                .save(output);
    }


    private void buildSmithingRecipes() {
        Ingredient template = Ingredient.of(ModItems.LB_BLUEPRINT);

        // Netherite Laser Blade by using Smithing Table
        SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(ModItems.LASER_BLADE), getIngredient(ConventionalItemTags.NETHERITE_INGOTS), RecipeCategory.TOOLS, ModItems.LASER_BLADE_FP)
                .unlocks("has_netherite_ingot", has(ConventionalItemTags.NETHERITE_INGOTS))
                .save(output, getItemId(ModItems.LASER_BLADE_FP) + "_smithing");

        // Upgrade Recipes
        addUpgradeRecipes(template);

        // Color Recipes
        addColorRecipes(template);

        // Model Change Recipes
        addModelChangeRecipes(template, Ingredient.of(Blocks.GLASS), 0);
        addModelChangeRecipes(template, Ingredient.of(Blocks.SAND), 1);
        addModelChangeRecipes(template, Ingredient.of(Blocks.SANDSTONE), 2);
        addModelChangeRecipes(template, Ingredient.of(Blocks.DIRT), 526);
    }

    private void addUpgradeRecipes(Ingredient template) {
        Map<Identifier, Upgrade> upgrades = UpgradeManager.getUpgrades();
        List<Identifier> excludedUpgradeIds = List.of(
                UpgradeID.EFFICIENCY_UPGRADE.getID(),
                UpgradeID.EFFICIENCY_REMOVER.getID(),
                UpgradeID.MENDING_UPGRADE.getID()
        );
        upgrades.forEach((id, upgrade) -> {
            TagKey<Item> tag = upgrade.ingredientItemTag();
            Ingredient ingredient = Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));

            if (!excludedUpgradeIds.contains(id)) {
                LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), ingredient, RecipeCategory.TOOLS, id)
                        .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                        .save(output, ToLaserBlade.MOD_ID + ":upgrade/lb_" + upgrade.shortName());

                // if (upgrade == UpgradeManager.getUpgrade(UpgradeID.FIREPROOF_UPGRADE)) {    // Forge
                //      return; // Fireproof upgrade is only available for not fireproof laser blade
                // }

                LBUpgradeRecipeBuilder.upgradeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), ingredient, RecipeCategory.TOOLS, id)
                        .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                        .save(output, ToLaserBlade.MOD_ID + ":upgrade/lbf_" + upgrade.shortName());
            }
        });
    }

    private void addColorRecipes(Ingredient template) {
        addInnerColorRecipes(template);
        addOuterColorRecipes(template);
        addGripColorRecipes(template);
    }

    private void addInnerColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.INNER_BLADE;
        addColorRecipe(template, Ingredient.of(Items.WHITE_STAINED_GLASS_PANE), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Items.ORANGE_STAINED_GLASS_PANE), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Items.MAGENTA_STAINED_GLASS_PANE), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_BLUE_STAINED_GLASS_PANE), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Items.YELLOW_STAINED_GLASS_PANE), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Items.LIME_STAINED_GLASS_PANE), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Items.PINK_STAINED_GLASS_PANE), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Items.GRAY_STAINED_GLASS_PANE), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_GRAY_STAINED_GLASS_PANE), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Items.CYAN_STAINED_GLASS_PANE), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Items.PURPLE_STAINED_GLASS_PANE), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Items.BLUE_STAINED_GLASS_PANE), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Items.BROWN_STAINED_GLASS_PANE), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Items.GREEN_STAINED_GLASS_PANE), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Items.RED_STAINED_GLASS_PANE), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Items.BLACK_STAINED_GLASS_PANE), part, LaserBladeColor.BLACK);

        addColorRecipe(template, Ingredient.of(Blocks.AMETHYST_BLOCK), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addOuterColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.OUTER_BLADE;
        addColorRecipe(template, Ingredient.of(Items.WHITE_STAINED_GLASS), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Items.ORANGE_STAINED_GLASS), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Items.MAGENTA_STAINED_GLASS), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_BLUE_STAINED_GLASS), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Items.YELLOW_STAINED_GLASS), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Items.LIME_STAINED_GLASS), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Items.PINK_STAINED_GLASS), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Items.GRAY_STAINED_GLASS), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Items.LIGHT_GRAY_STAINED_GLASS), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Items.CYAN_STAINED_GLASS), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Items.PURPLE_STAINED_GLASS), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Items.BLUE_STAINED_GLASS), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Items.BROWN_STAINED_GLASS), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Items.GREEN_STAINED_GLASS), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Items.RED_STAINED_GLASS), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Items.BLACK_STAINED_GLASS), part, LaserBladeColor.BLACK);

        addColorRecipe(template, Ingredient.of(Blocks.TINTED_GLASS), part, LaserBladeColor.SPECIAL_SWITCH_BLEND_MODE);
    }

    private void addGripColorRecipes(Ingredient template) {
        LaserBladeColorPart part = LaserBladeColorPart.GRIP;
        addColorRecipe(template, Ingredient.of(Blocks.WHITE_CARPET), part, LaserBladeColor.WHITE);
        addColorRecipe(template, Ingredient.of(Blocks.ORANGE_CARPET), part, LaserBladeColor.ORANGE);
        addColorRecipe(template, Ingredient.of(Blocks.MAGENTA_CARPET), part, LaserBladeColor.MAGENTA);
        addColorRecipe(template, Ingredient.of(Blocks.LIGHT_BLUE_CARPET), part, LaserBladeColor.LIGHT_BLUE);
        addColorRecipe(template, Ingredient.of(Blocks.YELLOW_CARPET), part, LaserBladeColor.YELLOW);
        addColorRecipe(template, Ingredient.of(Blocks.LIME_CARPET), part, LaserBladeColor.LIME);
        addColorRecipe(template, Ingredient.of(Blocks.PINK_CARPET), part, LaserBladeColor.PINK);
        addColorRecipe(template, Ingredient.of(Blocks.GRAY_CARPET), part, LaserBladeColor.GRAY);
        addColorRecipe(template, Ingredient.of(Blocks.LIGHT_GRAY_CARPET), part, LaserBladeColor.LIGHT_GRAY);
        addColorRecipe(template, Ingredient.of(Blocks.CYAN_CARPET), part, LaserBladeColor.CYAN);
        addColorRecipe(template, Ingredient.of(Blocks.PURPLE_CARPET), part, LaserBladeColor.PURPLE);
        addColorRecipe(template, Ingredient.of(Blocks.BLUE_CARPET), part, LaserBladeColor.BLUE);
        addColorRecipe(template, Ingredient.of(Blocks.BROWN_CARPET), part, LaserBladeColor.BROWN);
        addColorRecipe(template, Ingredient.of(Blocks.GREEN_CARPET), part, LaserBladeColor.GREEN);
        addColorRecipe(template, Ingredient.of(Blocks.RED_CARPET), part, LaserBladeColor.RED);
        addColorRecipe(template, Ingredient.of(Blocks.BLACK_CARPET), part, LaserBladeColor.BLACK);
    }

    private void addColorRecipe(Ingredient template, Ingredient addition, LaserBladeColorPart part, LaserBladeColor color) {
        int colorValue;

        switch(part) {
            case OUTER_BLADE -> colorValue = color.getOuterColor();
            case INNER_BLADE -> colorValue = color.getInnerColor();
            default -> colorValue = color.getGripColor();
        }

        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, RecipeCategory.TOOLS, part, colorValue)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(output, "tolaserblade:color/lb_" + part.getShortName() + "_" + color.getColorName());
        LBColorRecipeBuilder.colorRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, RecipeCategory.TOOLS, part, colorValue)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(output, ToLaserBlade.MOD_ID + ":color/lbf_" + part.getShortName() + "_" + color.getColorName());
    }

    private void addModelChangeRecipes(Ingredient template,  Ingredient addition, int type) {
        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE), addition, RecipeCategory.TOOLS, type)
                .unlockedBy("has_laser_blade", has(ModItems.LASER_BLADE))
                .save(output, "tolaserblade:model/lb_" + type);
        LBModelChangeRecipeBuilder.modelChangeRecipe(template, Ingredient.of(ModItems.LASER_BLADE_FP), addition, RecipeCategory.TOOLS, type)
                .unlockedBy("has_laser_blade_fp", has(ModItems.LASER_BLADE_FP))
                .save(output, "tolaserblade:model/lbf_" + type);
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private Ingredient getIngredient(TagKey<Item> tag) {
        return Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(tag));
    }

    public static class Runner extends FabricRecipeProvider {
        public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            return new TLBRecipeProvider(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "TLBRecipeProvider";
        }
    }
}
