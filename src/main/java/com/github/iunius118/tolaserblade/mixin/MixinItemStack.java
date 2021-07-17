package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    public void onGetAttributeModifiersHead(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) return;
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof LBSwordItem lbSwordItem)) return;
        if (stack.getOrCreateTag().contains("AttributeModifiers", 9)) return;

        // Return attribute modifiers to which Laser Blade upgrade was applied
        var attackPerformance = LaserBlade.performanceOf(stack).getAttackPerformance();
        Multimap<Attribute, AttributeModifier> attributeModifiers = lbSwordItem.getAttributeModifiers(attackPerformance.damage, attackPerformance.speed);
        cir.setReturnValue(attributeModifiers);
        cir.cancel();
    }
}
