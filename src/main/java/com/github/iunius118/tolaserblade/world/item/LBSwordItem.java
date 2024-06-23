package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    public static final float DEFAULT_DAMAGE = 3F;
    public static final float DEFAULT_SPEED = -1.2F;

    private final Tier tier;

    public static final FabricItemSettings properties = (FabricItemSettings) new FabricItemSettings()
            .customDamage(new LaserBladeCustomDamageHandler())
            .equipmentSlot(new LaserBladeEquipmentSlotProvider())
            .tab(ModCreativeModeTabs.TAB_LASER_BLADE);

    public LBSwordItem(boolean isFireResistant) {
        super(ModItemTiers.getLBSwordTier(isFireResistant), (int) DEFAULT_DAMAGE, DEFAULT_SPEED, LaserBladeItemBase.setFireResistant(properties, isFireResistant));

        tier = getTier();
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        // Allow all upgrades
        return true;
    }

    @Override
    public float getDamage() {
        return super.getDamage();
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return tier.getSpeed();
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        // Does not hurt and break
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(double attackDamage, double attackSpeed) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage + getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed + DEFAULT_SPEED, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, list, tooltipFlag);
    }
}
