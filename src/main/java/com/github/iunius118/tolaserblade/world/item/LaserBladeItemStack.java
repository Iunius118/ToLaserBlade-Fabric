package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
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
        LaserBladeVisual visual = LaserBlade.visualOf(stack);
        LaserBladeVisual.PartColor gripColor = visual.getGripColor();
        gripColor.color = LaserBladeColor.LIGHT_GRAY.getGripColor();
        visual.write(stack.getOrCreateTag());
        return stack;
    }
}
