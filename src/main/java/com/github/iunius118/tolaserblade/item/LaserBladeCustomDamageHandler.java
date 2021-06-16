package com.github.iunius118.tolaserblade.item;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class LaserBladeCustomDamageHandler implements CustomDamageHandler {
    @Override
    public int damage(ItemStack stack, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback) {
        // temporary value
        return 0;
    }
}
