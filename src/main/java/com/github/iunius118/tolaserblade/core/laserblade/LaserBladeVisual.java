package com.github.iunius118.tolaserblade.core.laserblade;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public record LaserBladeVisual(ModelType modelType, Coloring coloring) {
    public static LaserBladeVisual of(CompoundTag compoundTag) {
        var modelType = new ModelType(compoundTag);
        var coloring = Coloring.of(compoundTag);
        return new LaserBladeVisual(modelType, coloring);
    }

    public int getModelType() {
        return modelType.type;
    }

    public PartColor getInnerColor() {
        return coloring.bladeColor.innerColor;
    }

    public PartColor getOuterColor() {
        return coloring.bladeColor.outerColor;
    }

    public PartColor getGripColor() {
        return coloring.gripColor.gripColor;
    }

    public void setModelType(int type) {
        modelType.type = type;
    }

    public void setColorsByBiome(Level level, Holder<Biome> biomeHolder) {
        // Color by Biome type or Biome temperature
        ResourceKey<Level> dimension = level.dimension();

        if (Level.NETHER.equals(dimension)) {
            // The Nether
            setColorsByNetherBiome(level, biomeHolder);
        } else if (Level.END.equals(dimension)) {
            // The End
            getOuterColor().color = LaserBladeColor.WHITE.getBladeColor();
            getOuterColor().isSubtractColor = true;
            getInnerColor().isSubtractColor = true;
        } else {
            // Over-world etc.
            float temp = biomeHolder.value().getBaseTemperature();
            var laserBladeColor = LaserBladeColor.getColorByTemperature(temp);
            getOuterColor().color = laserBladeColor.getBladeColor();
        }
    }

    public void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
        getOuterColor().color = LaserBladeColor.WHITE.getBladeColor();

        if (compareBiome(level, biomeHolder, Biomes.SOUL_SAND_VALLEY) ||
                compareBiome(level, biomeHolder, Biomes.WARPED_FOREST)) {
            getOuterColor().isSubtractColor = true;

        } else {
            getInnerColor().isSubtractColor = true;
        }
    }

    private boolean compareBiome(Level level, Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
        if (level == null || biomeKey == null) return false;

        RegistryAccess registries = level.registryAccess();
        Registry<Biome> biomes = registries.registryOrThrow(Registry.BIOME_REGISTRY);
        Biome biome = biomeHolder.value();
        ResourceLocation biome1 = biomes.getKey(biome);
        ResourceLocation biome2 = biomeKey.location();

        return biome2.equals(biome1);
    }

    public void write(CompoundTag compoundTag) {
        modelType.write(compoundTag);
        coloring.write(compoundTag);
    }

    /* Inner classes */
    public static class ModelType { // This is also used in laser blade renderer so be public
        public int type = -1;

        private static final String KEY_TYPE = "type";

        public ModelType(CompoundTag compoundTag) {
            if (compoundTag.contains(KEY_TYPE, NbtType.INT)) {
                type = compoundTag.getInt(KEY_TYPE);
            }
        }

        public void write(CompoundTag compoundTag) {
            if (type >= 0) {
                compoundTag.putInt(KEY_TYPE, type);
            } else if (compoundTag.contains(KEY_TYPE, NbtType.INT)) {
                compoundTag.remove(KEY_TYPE);
            }
        }
    }

    private record Coloring(BladeColor bladeColor, GripColor gripColor) {
        public static Coloring of(CompoundTag compoundTag) {
            var bladeColor = BladeColor.of(compoundTag);
            var gripColor = GripColor.of(compoundTag);
            return new Coloring(bladeColor, gripColor);
        }

        public void write(CompoundTag compoundTag) {
            bladeColor.write(compoundTag);
            gripColor.write(compoundTag);
        }
    }

    private record BladeColor(PartColor innerColor, PartColor outerColor) {
        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public static BladeColor of(CompoundTag compoundTag) {
            var innerColor = new PartColor(compoundTag, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, LaserBladeColor.WHITE.getBladeColor());
            var outerColor = new PartColor(compoundTag, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, LaserBladeColor.RED.getBladeColor());
            return new BladeColor(innerColor, outerColor);
        }

        public void write(CompoundTag compoundTag) {
            compoundTag.putInt(KEY_INNER_COLOR, innerColor.color);
            compoundTag.putBoolean(KEY_IS_INNER_SUB_COLOR, innerColor.isSubtractColor);
            compoundTag.putInt(KEY_OUTER_COLOR, outerColor.color);
            compoundTag.putBoolean(KEY_IS_OUTER_SUB_COLOR, outerColor.isSubtractColor);
        }
    }

    private record GripColor(PartColor gripColor) {
        private static final String KEY_GRIP_COLOR = "colorG";

        public static GripColor of(CompoundTag compoundTag) {
            var gripColor = new PartColor(compoundTag, KEY_GRIP_COLOR, null, LaserBladeColor.WHITE.getBladeColor());
            return new GripColor(gripColor);
        }

        public int getGripColor() {
            return gripColor.color;
        }

        public void write(CompoundTag compoundTag) {
            compoundTag.putInt(KEY_GRIP_COLOR, gripColor.color);
        }
    }

    public static class PartColor {
        public int color = -1;
        public boolean isSubtractColor = false;

        public PartColor(CompoundTag compoundTag, String colorKey, String subKey, int defaultColor) {
            color = defaultColor;

            if (colorKey != null && compoundTag.contains(colorKey, NbtType.INT)) {
                color = compoundTag.getInt(colorKey);
            }

            if (subKey != null) {
                isSubtractColor = compoundTag.getBoolean(subKey);
            }
        }

        public void switchBlendMode() {
            isSubtractColor = !isSubtractColor;
        }
    }
}
