package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeTextKey;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserBladeItemUtil {
    public static void addLaserBladeInformation(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        LaserBlade laserBlade = LaserBlade.of(itemStack);
        boolean isFireResistant = laserBlade.isFireResistant();

        if (isFireResistant) {
            list.add(LaserBladeTextKey.KEY_TOOLTIP_FIRE_RESISTANT.translate().withStyle(ChatFormatting.GOLD));
        }
    }

    private LaserBladeItemUtil() {}
}
