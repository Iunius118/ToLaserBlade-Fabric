package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, list, tooltipFlag, upgradeType);
    }
}
