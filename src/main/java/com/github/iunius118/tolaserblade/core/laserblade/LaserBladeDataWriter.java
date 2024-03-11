package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.core.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class LaserBladeDataWriter {
    private final ItemStack itemStack;
    private final CompoundTag tag;

    private LaserBladeDataWriter(ItemStack itemStack) {
        this.itemStack = itemStack;
        CustomData data = itemStack.get(ModDataComponents.LASER_BLADE);

        if (data != null) {
            tag = data.copyTag();
        } else{
            tag = creatTag(itemStack);
        }
    }

    private CompoundTag creatTag(ItemStack itemStack) {
        CompoundTag newTag = new CompoundTag();
        
        if (!itemStack.has(DataComponents.CUSTOM_DATA)) {
            // Laser blade doesn't have any tags
            return newTag;
        }

        // Migrate CompoundTag from old NBT data
        CompoundTag oldTag = itemStack.get(DataComponents.CUSTOM_DATA).copyTag();
        
        if (oldTag.contains(LaserBlade.KEY_ATK, Tag.TAG_FLOAT)) {
            newTag.putFloat(LaserBlade.KEY_ATK, oldTag.getFloat(LaserBlade.KEY_ATK));
            oldTag.remove(LaserBlade.KEY_ATK);
        }

        if (oldTag.contains(LaserBlade.KEY_SPD, Tag.TAG_FLOAT)) {
            newTag.putFloat(LaserBlade.KEY_SPD, oldTag.getFloat(LaserBlade.KEY_SPD));
            oldTag.remove(LaserBlade.KEY_SPD);
        }

        if (oldTag.contains(LaserBladeVisual.KEY_MODEL_TYPE, Tag.TAG_INT)) {
            newTag.putInt(LaserBladeVisual.KEY_MODEL_TYPE, oldTag.getInt(LaserBladeVisual.KEY_MODEL_TYPE));
            oldTag.remove(LaserBladeVisual.KEY_MODEL_TYPE);
        }

        if (oldTag.contains(LaserBladeVisual.KEY_INNER_COLOR, Tag.TAG_INT)) {
            newTag.putInt(LaserBladeVisual.KEY_INNER_COLOR, oldTag.getInt(LaserBladeVisual.KEY_INNER_COLOR));
            oldTag.remove(LaserBladeVisual.KEY_INNER_COLOR);
        }
        
        if (oldTag.contains(LaserBladeVisual.KEY_OUTER_COLOR, Tag.TAG_INT)) {
            newTag.putInt(LaserBladeVisual.KEY_OUTER_COLOR, oldTag.getInt(LaserBladeVisual.KEY_OUTER_COLOR));
            oldTag.remove(LaserBladeVisual.KEY_OUTER_COLOR);
        }

        if (oldTag.contains(LaserBladeVisual.KEY_GRIP_COLOR, Tag.TAG_INT)) {
            newTag.putInt(LaserBladeVisual.KEY_GRIP_COLOR, oldTag.getInt(LaserBladeVisual.KEY_GRIP_COLOR));
            oldTag.remove(LaserBladeVisual.KEY_GRIP_COLOR);
        }
        
        if (oldTag.contains(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR, Tag.TAG_BYTE)) {
            newTag.putBoolean(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR, oldTag.getBoolean(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR));
            oldTag.remove(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR);
        }
        
        if (oldTag.contains(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR, Tag.TAG_BYTE)) {
            newTag.putBoolean(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR, oldTag.getBoolean(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR));
            oldTag.remove(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR);
        }

        if (oldTag.isEmpty()) {
            // Remove custom_data when custom_data tag is empty
            itemStack.remove(DataComponents.CUSTOM_DATA);
        } else {
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(oldTag));
        }

        itemStack.set(ModDataComponents.LASER_BLADE, CustomData.of(newTag));
        return newTag;
    }

    public static LaserBladeDataWriter of(ItemStack itemStack) {
        return new LaserBladeDataWriter(itemStack);
    }

    public void write() {
        itemStack.set(ModDataComponents.LASER_BLADE, CustomData.of(tag));
    }

    public LaserBladeDataWriter damage(float damage) {
        tag.putFloat(LaserBlade.KEY_ATK, Mth.clamp(damage, LaserBlade.MOD_ATK_MIN, LaserBlade.MOD_ATK_MAX));
        return this;
    }

    public LaserBladeDataWriter speed(float speed) {
        tag.putFloat(LaserBlade.KEY_SPD, Mth.clamp(speed, LaserBlade.MOD_SPD_MIN, LaserBlade.MOD_SPD_MAX));
        return this;
    }

    @Deprecated
    public LaserBladeDataWriter type(int type) {
        tag.putInt(LaserBlade.KEY_TYPE, Math.max(type, 0));
        return this;
    }

    public LaserBladeDataWriter modelType(int modelType) {
        tag.putInt(LaserBladeVisual.KEY_MODEL_TYPE, Math.max(modelType, 0));
        return this;
    }

    public LaserBladeDataWriter innerColor(int color) {
        tag.putInt(LaserBladeVisual.KEY_INNER_COLOR, color);
        return this;
    }

    public LaserBladeDataWriter isInnerSubColor(boolean isSubColor) {
        tag.putBoolean(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR, isSubColor);
        return this;
    }

    public LaserBladeDataWriter switchIsInnerSubColor() {
        tag.putBoolean(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR, !tag.getBoolean(LaserBladeVisual.KEY_IS_INNER_SUB_COLOR));
        return this;
    }

    public LaserBladeDataWriter outerColor(int color) {
        tag.putInt(LaserBladeVisual.KEY_OUTER_COLOR, color);
        return this;
    }

    public LaserBladeDataWriter isOuterSubColor(boolean isSubColor) {
        tag.putBoolean(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR, isSubColor);
        return this;
    }

    public LaserBladeDataWriter switchIsOuterSubColor() {
        tag.putBoolean(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR, !tag.getBoolean(LaserBladeVisual.KEY_IS_OUTER_SUB_COLOR));
        return this;
    }

    public LaserBladeDataWriter gripColor(int color) {
        tag.putInt(LaserBladeVisual.KEY_GRIP_COLOR, color);
        return this;
    }

    // Color by Biome type or Biome temperature
    public LaserBladeDataWriter setColorsByBiome(Level level, Holder<Biome> biomeHolder) {
        ResourceKey<Level> dimension = level.dimension();

        if (Level.NETHER.equals(dimension)) {
            // The Nether
            setColorsByNetherBiome(level, biomeHolder);
        } else if (Level.END.equals(dimension)) {
            // The End
            setColorsByEndBiome(level, biomeHolder);
        } else {
            // Over-world etc.
            setColorsByOverWorldBiome(level, biomeHolder);
        }

        return this;
    }

    private void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
        outerColor(LaserBladeColor.WHITE.getBladeColor());

        if (compareBiomes(biomeHolder, Biomes.SOUL_SAND_VALLEY) || compareBiomes(biomeHolder, Biomes.WARPED_FOREST)) {
            isOuterSubColor(true);
        } else {
            isInnerSubColor(true);
        }
    }

    private void setColorsByEndBiome(Level level, Holder<Biome> biomeHolder) {
        outerColor(LaserBladeColor.WHITE.getBladeColor()).isOuterSubColor(true).isInnerSubColor(true);
    }

    private void setColorsByOverWorldBiome(Level level, Holder<Biome> biomeHolder) {
        if (compareBiomes(biomeHolder, Biomes.DEEP_DARK)) {
            // Deep dark biome
            setDeepDarkColors();
        } else if (compareBiomes(biomeHolder, Biomes.CHERRY_GROVE)) {
            // Cherry grove biome
            setCherryGroveColors();
        } else {
            float temp = biomeHolder.value().getBaseTemperature();
            int color = LaserBladeColor.getColorByTemperature(temp).getBladeColor();
            outerColor(color);
        }
    }

    private void setDeepDarkColors() {
        outerColor(LaserBladeColor.CYAN.getBladeColor())
                .innerColor(0xFFFADCD7)    // Sculk's deep dark blue (negative)
                .isInnerSubColor(true)
                .gripColor(0xFF052328)     // Sculk's deep dark blue
                .modelType(2);
    }

    private void setCherryGroveColors() {
        outerColor(LaserBladeColor.PINK.getBladeColor())
                .innerColor(0xFFFADCF0)    // Cherry blossom's light pink
                .gripColor(0xFF4B2D3C);    // Cherry log's dark brown
    }

    private boolean compareBiomes(Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
        if (biomeHolder == null)
            return false;

        return biomeHolder.is(biomeKey);
    }
}
