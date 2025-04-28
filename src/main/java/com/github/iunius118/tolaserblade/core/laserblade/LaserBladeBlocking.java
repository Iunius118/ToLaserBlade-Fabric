package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.component.BlocksAttacks;

import java.util.List;
import java.util.Optional;

public class LaserBladeBlocking {
    public static BlocksAttacks getBlocksAttackComponent(boolean isFireResistant) {
        var blockingSoundHolder = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK : ModSoundEvents.ITEM_LASER_BLADE_BLOCK;
        return new BlocksAttacks(
                0.25F,
                1.0F,
                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                new BlocksAttacks.ItemDamageFunction(0.0F, 0.0F, 0.0F),
                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                Optional.of(blockingSoundHolder),
                Optional.of(SoundEvents.SHIELD_BREAK)
        );
    }
}
