package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
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
    public LBSwordItem(Properties properties, boolean isFireResistant) {
        super(ModToolMaterials.getLBSwordMaterial(isFireResistant), LaserBlade.BASE_ATTACK, LaserBlade.BASE_SPEED, properties);
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
        var tool = itemStack.get(DataComponents.TOOL);
        return tool != null ? tool.defaultMiningSpeed() : 1.0F;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        // Does not hurt and break
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        // Does not hurt and break
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
