package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LBPartItem extends Item implements LaserBladeItemBase {
    public final Upgrade.Type upgradeType;

    public LBPartItem(Upgrade.Type type, boolean isFireResistant) {
        super(LaserBladeItemBase.setFireResistant(new Item.Properties(), isFireResistant));
        upgradeType = type;
    }

    public LBPartItem(Upgrade.Type type) {
        this(type, false);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, upgradeType);
    }
}
