package com.github.iunius118.tolaserblade.mixin.client;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow
    public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext,
                                    PoseStack poseStack, MultiBufferSource multiBufferSource, int i);

    @Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;F" +
            "Lnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress,
            ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, CallbackInfo ci
    ) {
        if (!player.isScoping()) {
            if (!stack.isEmpty() && stack.getItem() instanceof LBSwordItem) {
                // When laser blade is in player's hand
                boolean isInMainHand = (hand == InteractionHand.MAIN_HAND);
                var humanoidArm = isInMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
                var interactionHand = (player.getMainArm() == humanoidArm) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

                if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == interactionHand) {
                    // When player is using laser blade (e.g. blocking with laser blade)
                    poseStack.pushPose();

                    boolean isRightArm = (humanoidArm == HumanoidArm.RIGHT);
                    poseStack.translate(isRightArm ? 0.56F : -0.56F, -0.52F + equippedProgress * -0.6F, -0.72F);
                    // Render the laser blade item in the player's hand
                    renderItem(player, stack,
                            isRightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                            poseStack, buffer, combinedLight);

                    poseStack.popPose();

                    // Cancel
                    ci.cancel();
                }
            }
        }
    }
}
