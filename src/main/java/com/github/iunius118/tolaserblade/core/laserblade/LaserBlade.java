package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public record LaserBlade(LaserBladePerformance performance, LaserBladeVisual visual) {
    public static LaserBlade of(ItemStack stack) {
        var item = stack.getItem();
        var compoundTag = stack.hasTag() ? stack.getTag() : new CompoundTag();
        var performance = LaserBladePerformance.of(compoundTag, item.isFireResistant());
        var visual = LaserBladeVisual.of(compoundTag);
        return new LaserBlade(performance, visual);
    }

    public static LaserBladePerformance performanceOf(ItemStack stack) {
        var item = stack.getItem();
        var compoundTag = stack.hasTag() ? stack.getTag() : new CompoundTag();
        return LaserBladePerformance.of(compoundTag, item.isFireResistant());
    }

    public static LaserBladeVisual visualOf(ItemStack stack) {
        var compoundTag = stack.hasTag() ? stack.getTag() : new CompoundTag();
        return LaserBladeVisual.of(compoundTag);
    }

    public LaserBladePerformance.AttackPerformance getAttackPerformance() {
        return performance.getAttackPerformance();
    }

    public boolean isFireResistant() {
        return performance.isFireResistant();
    }

    public LaserBladeVisual getVisual() {
        return visual;
    }

    public void write(ItemStack stack) {
        var compoundTag = stack.getOrCreateTag();
        performance.write(compoundTag);
        visual.write(compoundTag);
    }
}
