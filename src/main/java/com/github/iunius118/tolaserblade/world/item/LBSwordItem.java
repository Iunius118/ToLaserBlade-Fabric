package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.mixin.ItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class LBSwordItem extends Item implements LaserBladeItemBase {
    public LBSwordItem(Properties properties, boolean isFireResistant) {
        // Apply sword properties
        super(ModToolMaterials.getLBSwordMaterial(isFireResistant).applySwordProperties(properties, LaserBlade.BASE_ATTACK, LaserBlade.BASE_SPEED));
        // ... and overwrite the tool component
        overwriteToolComponent(isFireResistant);
    }

    private void overwriteToolComponent(boolean isFireResistant) {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(this.components());
        float speed = ModToolMaterials.getLBSwordMaterial(isFireResistant).speed();
        builder.set(DataComponents.TOOL, new Tool(List.of(), speed, 1, false));
        builder.set(DataComponents.WEAPON, new Weapon(0));
        // Debug: add BLOCKS_ATTACKS data component to laser blade
        // builder.set(DataComponents.BLOCKS_ATTACKS, LaserBladeBlocking.getBlocksAttackComponent(isFireResistant));
        ((ItemAccessor) this).setComponents(builder.build());
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
    public void appendTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag flag, List<Component> tooltip) {
        ArrayList<Component> myTooltip = new ArrayList<>();
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, myTooltip, flag, Upgrade.Type.OTHER);
        tooltip.addAll(1, myTooltip);
    }
}
