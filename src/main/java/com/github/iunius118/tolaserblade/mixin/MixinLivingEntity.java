package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void onSwing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        var livingEntity = LivingEntity.class.cast(this);
        var itemStack = (livingEntity).getItemInHand(interactionHand);
        if (!itemStack.isEmpty()) onEntitySwing(livingEntity, itemStack);
    }

    @Unique
    private void onEntitySwing(LivingEntity livingEntity, ItemStack itemStack) {
        var level = livingEntity.level();

        if (!level.isClientSide
                && itemStack.getItem() instanceof LBSwordItem
                && !livingEntity.swinging) {
            // Server side
            // When laser blade swung
            // Play sound of laser blade when it's swung
            LaserBladeItemUtil.playSwingSound(level, livingEntity, itemStack);
        }
    }
}
