package com.github.iunius118.tolaserblade.core.dispenser;

import com.github.iunius118.tolaserblade.common.util.LaserTrapPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseLBSwordBehavior implements DispenseItemBehavior {
    public static final DispenseItemBehavior DEFAULT_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior();

    @Override
    public ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
        var serverLevel = blockSource.getLevel();
        var pos = blockSource.getPos();
        var dir = blockSource.getBlockState().getValue(DispenserBlock.FACING);
        attackEntities(serverLevel, pos, dir, itemStack);
        return itemStack;
    }

    private void attackEntities(ServerLevel serverLevel, BlockPos trapPos, Direction dir, ItemStack stack) {
        var laserTrapPlayer = LaserTrapPlayer.get(serverLevel, trapPos, stack);
        laserTrapPlayer.attackEntities(dir);
        laserTrapPlayer.remove(Entity.RemovalReason.DISCARDED);
    }
}
