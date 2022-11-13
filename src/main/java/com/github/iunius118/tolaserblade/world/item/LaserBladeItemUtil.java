package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserBladeItemUtil {
    public static void addLaserBladeInformation(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        // LaserBlade laserBlade = LaserBlade.of(itemStack.copy());
        boolean isFireResistant = itemStack.getItem().isFireResistant();

        if (isFireResistant) {
            list.add(LaserBladeTextKey.KEY_TOOLTIP_FIRE_RESISTANT.translate().withStyle(ChatFormatting.GOLD));
        }
    }

    public static void playSwingSound(Level level, LivingEntity entity, boolean isFireResistant) {
        SoundEvent soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    private LaserBladeItemUtil() {}
}
