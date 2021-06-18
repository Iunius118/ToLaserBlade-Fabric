package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LaserBlade {
    private final LaserBladePerformance performance;
    private final LaserBladeVisual visual;

    public LaserBlade(ItemStack stack) {
        Item item = stack.getItem();
        CompoundTag compoundTag = stack.getOrCreateTag();
        performance = new LaserBladePerformance(compoundTag, item.isFireResistant());
        visual = new LaserBladeVisual(compoundTag);
    }

    public static LaserBlade of(ItemStack stack) {
        return new LaserBlade(stack);
    }

    public static LaserBladePerformance performanceOf(ItemStack stack) {
        Item item = stack.getItem();
        CompoundTag compoundTag = stack.getOrCreateTag();
        return new LaserBladePerformance(compoundTag, item.isFireResistant());
    }

    public static LaserBladeVisual visualOf(ItemStack stack) {
        Item item = stack.getItem();
        CompoundTag compoundTag = stack.getOrCreateTag();
        return new LaserBladeVisual(compoundTag);
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
        CompoundTag compoundTag = stack.getOrCreateTag();
        performance.write(compoundTag);
        visual.write(compoundTag);
    }
}
