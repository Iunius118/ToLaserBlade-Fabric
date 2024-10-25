package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class MixinSmithingMenu extends ItemCombinerMenu {
    private MixinSmithingMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, Level level) {
        super(MenuType.SMITHING, i, inventory, containerLevelAccess, createInputSlotDefinitions(level.recipeAccess()));
    }

    @Unique
    private static ItemCombinerMenuSlotDefinition createInputSlotDefinitions(RecipeAccess recipeAccess) {
        RecipePropertySet recipePropertySet = recipeAccess.propertySet(RecipePropertySet.SMITHING_BASE);
        RecipePropertySet recipePropertySet2 = recipeAccess.propertySet(RecipePropertySet.SMITHING_TEMPLATE);
        RecipePropertySet recipePropertySet3 = recipeAccess.propertySet(RecipePropertySet.SMITHING_ADDITION);
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 8, 48, recipePropertySet2::test)
                .withSlot(1, 26, 48, recipePropertySet::test)
                .withSlot(2, 44, 48, recipePropertySet3::test)
                .withResultSlot(3, 98, 48)
                .build();
    }

    @Inject(method = "shrinkStackInSlot(I)V", at = @At("HEAD"), cancellable = true)
    private void onShrinkStack(int slot, CallbackInfo ci) {
        // When a laser blade blueprint is in the template slot, it is not consumed.
        if (slot == SmithingMenu.TEMPLATE_SLOT && super.inputSlots.getItem(slot).is(ModItems.LB_BLUEPRINT)) {
            ci.cancel();
        }
    }
}
