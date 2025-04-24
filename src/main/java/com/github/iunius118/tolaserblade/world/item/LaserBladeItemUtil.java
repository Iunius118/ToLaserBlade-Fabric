package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LaserBladeItemUtil {
    public static boolean isFireResistant(ItemStack itemStack) {
        var damageResistant = itemStack.get(DataComponents.DAMAGE_RESISTANT);
        return (damageResistant != null) && (damageResistant.types() == DamageTypeTags.IS_FIRE);
    }

    public static void playSwingSound(Level level, LivingEntity entity, ItemStack itemStack) {
        var soundEvent = isFireResistant(itemStack) ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position();
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void playHitSound(Level level, Entity target, ItemStack itemStack) {
        var soundEvent = isFireResistant(itemStack) ? ModSoundEvents.ITEM_LASER_BLADE_FP_HIT : ModSoundEvents.ITEM_LASER_BLADE_HIT;
        Vec3 pos = target.position().add(0, target.getEyeHeight(), 0);
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, target.getSoundSource(), 1.0F, 1.0F);
    }

    public static InteractionResult onPlayerAttackEntity(Player player, Level level, InteractionHand hand, Entity target, @Nullable EntityHitResult hitResult) {
        if (!level.isClientSide() && !player.isSpectator() && target != null) {
            var itemStack = player.getItemInHand(hand);

            if (itemStack.getItem() instanceof LBSwordItem) {
                playHitSound(level, target, itemStack);
            }
        }

        return InteractionResult.PASS;
    }

    public static void addLaserBladeInformation(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag, Upgrade.Type upgradeType) {
        // List of components to add to the item tooltip
        ArrayList<Component> myTooltip = new ArrayList<>();

        if (isFireResistant(itemStack)) {
            myTooltip.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.translate().withStyle(ChatFormatting.GOLD));
        }

        switch (upgradeType) {
            case BATTERY -> addAttackSpeed(myTooltip, LaserBlade.getSpeed(itemStack));
            case MEDIUM -> addAttackDamage(myTooltip, LaserBlade.getAttack(itemStack));
            case CASING, OTHER -> addModelType(myTooltip, itemStack);
            default -> {}
        }

        // Add the list to the item tooltip
        tooltip.addAll(1, myTooltip);
    }

    private static void addModelType(List<Component> tooltip, ItemStack itemStack) {
        int modelType = LaserBladeAppearance.of(itemStack).getType();

        if (modelType >= 0) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.translate(modelType).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    private static void addAttackDamage(List<Component> tooltip, float atk) {
        if (atk <= -0.005F || atk >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), atk));
        }
    }

    private static void addAttackSpeed(List<Component> tooltip, float spd) {
        if (spd <= -0.005F || spd >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), spd));
        }
    }

    private static Component getUpgradeTextComponent(String key, float value) {
        return Component.translatable(key, (value < 0 ? "" : "+") + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value))
                .withStyle(ChatFormatting.DARK_GREEN);
    }

    private LaserBladeItemUtil() {}
}
