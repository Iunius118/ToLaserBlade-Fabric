package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    public LBSwordItem( boolean isFireResistant) {
        super(ModItemTiers.getLBSwordTier(isFireResistant),
                LaserBladeItemBase.setFireResistant(new Item.Properties(), isFireResistant)
                        .attributes(SwordItem.createAttributes(ModItemTiers.getLBSwordTier(isFireResistant), 3, LaserBlade.BASE_SPEED)));
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        LaserBlade.updateItemAttributeModifiers(stack);
        LaserBladeAppearance.of(stack);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        // Allow all upgrades
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return this.getTier().getSpeed();
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        // Does not hurt and break
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
