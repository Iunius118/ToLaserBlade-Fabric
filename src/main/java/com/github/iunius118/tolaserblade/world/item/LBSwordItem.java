package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    public static final float DEFAULT_DAMAGE = 3F;
    public static final float DEFAULT_SPEED = -1.2F;

    private final Tier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public LBSwordItem( boolean isFireResistant) {
        super(ModItemTiers.getLBSwordTier(isFireResistant),
                LaserBladeItemBase.setFireResistant(new Item.Properties(), isFireResistant)
                        .attributes(SwordItem.createAttributes(ModItemTiers.getLBSwordTier(isFireResistant), 3, DEFAULT_SPEED)));

        tier = getTier();
        attackDamage = DEFAULT_DAMAGE + tier.getAttackDamageBonus();
        attackSpeed = DEFAULT_SPEED;
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        float attackUpgrade = LaserBlade.getAttack(stack);
        float speedUpgrade = LaserBlade.getSpeed(stack);
        var itemAttributeModifiers = createAttributes(attackUpgrade, speedUpgrade);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
        LaserBladeAppearance.of(stack);
    }

    private ItemAttributeModifiers createAttributes(float attackUpgrade, float speedUpgrade) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackUpgrade + attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speedUpgrade + attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        // Allow all upgrades
        return true;
    }

    public float getAttackDamage() {
        return attackDamage;
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
    public boolean isCorrectToolForDrops(ItemStack itemStack, BlockState blockState) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, Upgrade.Type.OTHER);
    }
}
