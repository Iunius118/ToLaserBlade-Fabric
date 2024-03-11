package com.github.iunius118.tolaserblade.world.item;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LaserBladeCustomDamageHandler implements CustomDamageHandler {
    @Override
    public int damage(ItemStack stack, int amount, LivingEntity entity, EquipmentSlot slot, Runnable breakCallback) {
        // temporary value
        return 0;
    }
}
