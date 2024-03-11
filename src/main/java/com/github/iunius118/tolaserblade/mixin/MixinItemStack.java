package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V", at = @At("HEAD"))
    public void onForEachModifier(EquipmentSlot equipmentSlot, BiConsumer<Holder<Attribute>, AttributeModifier> biConsumer, CallbackInfo ci) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) return;
        ItemStack stack = (ItemStack)(Object) this;
        if (!(stack.getItem() instanceof LBSwordItem lbSwordItem)) return;

        // Return attribute modifiers to which Laser Blade upgrade was applied
        ItemAttributeModifiers modifiers = lbSwordItem.getAttributeModifiers(stack);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
    }
}
