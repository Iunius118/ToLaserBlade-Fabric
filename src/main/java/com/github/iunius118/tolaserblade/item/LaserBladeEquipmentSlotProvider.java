package com.github.iunius118.tolaserblade.item;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class LaserBladeEquipmentSlotProvider implements EquipmentSlotProvider {
    @Override
    public EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
