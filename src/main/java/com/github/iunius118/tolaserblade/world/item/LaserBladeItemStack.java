package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public enum LaserBladeItemStack {
    ORIGINAL(() -> new ItemStack(ModItems.LASER_BLADE)),
    ICON(LaserBladeItemStack::getIconStack),
    FP(() -> new ItemStack(ModItems.LASER_BLADE_FP)),
    ;

    private final Supplier<ItemStack> supplier;

    LaserBladeItemStack(Supplier<ItemStack> supplier) {
        this.supplier = supplier;
    }

    public Supplier<ItemStack> getSupplier() {
        return supplier;
    }

    public ItemStack getCopy() {
        return supplier.get();
    }

    private static ItemStack getIconStack() {
        ItemStack stack = new ItemStack(ModItems.LASER_BLADE);
        LaserBladeVisual.Writer.of(stack).writeGripColor(LaserBladeColor.LIGHT_GRAY.getGripColor());
        return stack;
    }

    public static ItemStack getModelChangedStack(int type, boolean isFireproof) {
        ItemStack stack = new ItemStack(isFireproof ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBladeVisual.Writer.of(stack).writeModelType(type);
        return stack;
    }
}