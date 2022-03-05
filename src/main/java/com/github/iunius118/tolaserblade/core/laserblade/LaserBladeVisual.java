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

public class LaserBladeVisual {
    private final ModelType modelType;
    private final Coloring coloring;


    public LaserBladeVisual(CompoundTag compoundTag) {
        modelType = new ModelType(compoundTag);
        coloring = new Coloring(compoundTag);
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
        switch (Biome.getBiomeCategory(biomeHolder)) {
            case NETHER -> {
                // The Nether
                setColorsByNetherBiome(level, biomeHolder);
            }
            case THEEND -> {
                // The End
                getOuterColor().color = LaserBladeColor.WHITE.getBladeColor();
                getOuterColor().isSubtractColor = true;
                getInnerColor().isSubtractColor = true;
            }
            default -> {
                // Biomes on Over-level etc.
                float temp = biomeHolder.value().getBaseTemperature();
                setColorsByTemperature(temp);
            }
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

    public void setColorsByTemperature(float temp) {
        if (temp > 1.5F) {
            // t > 1.5
            getOuterColor().color = LaserBladeColor.TEMP_DESERT.getBladeColor();
        } else if (temp > 1.0F) {
            // 1.5 >= t > 1.0
            getOuterColor().color = LaserBladeColor.TEMP_SAVANNA.getBladeColor();
        } else if (temp > 0.8F) {
            // 1.0 >= t > 0.8
            getOuterColor().color = LaserBladeColor.TEMP_JUNGLE.getBladeColor();
        } else if (temp >= 0.5F) {
            // 0.8 >= t >= 0.5
            getOuterColor().color = LaserBladeColor.RED.getBladeColor();
        } else if (temp >= 0.2F) {
            // 0.5 > t >= 0.2
            getOuterColor().color = LaserBladeColor.TEMP_TAIGA.getBladeColor();
        } else if (temp >= -0.25F) {
            // 0.2 > t >= -0.25
            getOuterColor().color = LaserBladeColor.TEMP_ICE_PLAIN.getBladeColor();
        } else {
            // -0.25 > t
            getOuterColor().color = LaserBladeColor.TEMP_SNOWY_TAIGA.getBladeColor();
        }
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

    private static class Coloring {
        private final BladeColor bladeColor;
        private final GripColor gripColor;

        public Coloring(CompoundTag compoundTag) {
            bladeColor = new BladeColor(compoundTag);
            gripColor = new GripColor(compoundTag);
        }

        public void write(CompoundTag compoundTag) {
            bladeColor.write(compoundTag);
            gripColor.write(compoundTag);
        }
    }

    private static class BladeColor {
        public PartColor innerColor;
        public PartColor outerColor;

        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public BladeColor(CompoundTag compoundTag) {
            innerColor = new PartColor(compoundTag, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, LaserBladeColor.WHITE.getBladeColor());
            outerColor = new PartColor(compoundTag, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, LaserBladeColor.RED.getBladeColor());
        }

        public void write(CompoundTag compoundTag) {
            compoundTag.putInt(KEY_INNER_COLOR, innerColor.color);
            compoundTag.putBoolean(KEY_IS_INNER_SUB_COLOR, innerColor.isSubtractColor);
            compoundTag.putInt(KEY_OUTER_COLOR, outerColor.color);
            compoundTag.putBoolean(KEY_IS_OUTER_SUB_COLOR, outerColor.isSubtractColor);
        }
    }

    private static class GripColor {
        private PartColor gripColor;

        private static final String KEY_GRIP_COLOR = "colorG";

        public GripColor(CompoundTag compoundTag) {
            gripColor = new PartColor(compoundTag, KEY_GRIP_COLOR, null, LaserBladeColor.WHITE.getBladeColor());
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
