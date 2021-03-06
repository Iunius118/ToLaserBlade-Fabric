package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLBSwordBehavior;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DispenserBlock;

public class CommonRegister {
    public static void registerEventListeners() {

    }

    public static void registerRecipeSerializers() {
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "color"), ModRecipeSerializers.COLOR);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "upgrade"), ModRecipeSerializers.UPGRADE);
    }

    public static void registerItems() {
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade"), ModItems.LASER_BLADE);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_fp"), ModItems.LASER_BLADE_FP);
    }

    public static void registerEnchantments() {
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(ToLaserBlade.MOD_ID, "light_element"), ModEnchantments.LIGHT_ELEMENT);
    }

    public static void registerDispenseItemBehaviors() {
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE, new DispenseLBSwordBehavior());
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE_FP, new DispenseLBSwordBehavior());
    }

    public static void registerParticleTypes() {
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_x"), ModParticleTypes.LASER_TRAP_X);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_y"), ModParticleTypes.LASER_TRAP_Y);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap_z"), ModParticleTypes.LASER_TRAP_Z);
    }

    private CommonRegister() {}
}
