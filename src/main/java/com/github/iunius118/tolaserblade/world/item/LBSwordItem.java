package com.github.iunius118.tolaserblade.world.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    private final Tier tier;

    public static final FabricItemSettings properties = (FabricItemSettings) new FabricItemSettings()
            .customDamage(new LaserBladeCustomDamageHandler())
            .equipmentSlot(new LaserBladeEquipmentSlotProvider())
            .tab(ModCreativeModeTabs.TAB_LASER_BLADE);

    public LBSwordItem(boolean isFireResistant) {
        super(new LBSwordItemTier(isFireResistant), 3, -1.2F, LaserBladeItemBase.setFireResistant(properties, isFireResistant));

        tier = getTier();
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
        if (blockState.getDestroySpeed(level, blockPos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, living -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockState) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, list, tooltipFlag);
    }
}
