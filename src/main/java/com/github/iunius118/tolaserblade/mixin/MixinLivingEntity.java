package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
    private void onSwing(InteractionHand interactionHand, boolean bl, CallbackInfo ci) {
        var livingEntity = (LivingEntity) (Object) this;
        var itemStack = (livingEntity).getItemInHand(interactionHand);
        if (!itemStack.isEmpty()) onEntitySwing(livingEntity, itemStack);
    }

    private void onEntitySwing(LivingEntity livingEntity, ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof LBSwordItem lbSwordItem)) return;
        // When laser blade swung

        var level = livingEntity.level;

        if (!level.isClientSide && livingEntity instanceof Player player && !player.swinging) {
            // Server side
            // Play sound of laser blade when it's swung
            LaserBladeItemUtil.playSwingSound(level, livingEntity, lbSwordItem.isFireResistant());
        }
    }
}
