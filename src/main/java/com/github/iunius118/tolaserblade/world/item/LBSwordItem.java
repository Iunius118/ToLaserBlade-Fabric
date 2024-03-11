package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    public static final float DEFAULT_DAMAGE = 3F;
    public static final float DEFAULT_SPEED = -1.2F;
    
    private final float damage;

    private LBSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
        damage = DEFAULT_DAMAGE + tier.getAttackDamageBonus();
    }

    public static LBSwordItem creatLBSwordItem(boolean isFireResistant) {
        Tier tier = ModItemTiers.getLBSwordTier(isFireResistant);
        ItemAttributeModifiers itemAttributeModifiers = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", DEFAULT_DAMAGE + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", DEFAULT_SPEED, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
        Item.Properties properties = new Item.Properties()
                .customDamage(new LaserBladeCustomDamageHandler())
                .equipmentSlot(new LaserBladeEquipmentSlotProvider())
                .attributes(itemAttributeModifiers);
        return new LBSwordItem(tier, LaserBladeItemBase.setFireResistant(properties, isFireResistant));
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        // Allow all upgrades
        return true;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return this.getTier().getSpeed();
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (blockState.getDestroySpeed(level, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return true;
    }

    public ItemAttributeModifiers getAttributeModifiers(ItemStack stack) {
        var laserBlade = LaserBlade.of(stack);
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", laserBlade.getDamage() + getDamage(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", laserBlade.getSpeed() + DEFAULT_SPEED, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, list, tooltipFlag, Upgrade.Type.OTHER);
    }
}
