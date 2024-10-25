package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
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
        var stack = new ItemStack(ModItems.LASER_BLADE);
        new LaserBladeAppearance().setGripColor(LaserBladeColor.LIGHT_GRAY.getGripColor()).setTo(stack);
        return stack;
    }

    public static ItemStack getModelChangedStack(int type, boolean isFireResistant) {
        var stack = new ItemStack(isFireResistant ? ModItems.LASER_BLADE_FP : ModItems.LASER_BLADE);
        LaserBladeAppearance.of().setType(type).setTo(stack);
        return stack;
    }
}
