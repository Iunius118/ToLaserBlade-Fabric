package com.github.iunius118.tolaserblade.world.item.enchantment;

import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LightElementEnchantment extends DamageEnchantment {
    private static final int MAX_LEVEL = 10;

    public LightElementEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, 1, 11, 20, Optional.empty(), EquipmentSlot.MAINHAND);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public float getDamageBonus(int level, @Nullable EntityType<?> entityType) {
        if (entityType != null && (entityType.is(EntityTypeTags.UNDEAD) || entityType.is(EntityTypeTags.ILLAGER))) {
            return (float) level * 2.4F;
        }

        return (float) level * 0.4F;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof LaserBladeItemBase;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
    }
}
