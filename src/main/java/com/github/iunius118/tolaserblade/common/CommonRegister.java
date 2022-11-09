package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLBSwordBehavior;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.integration.autoconfig.ModConfig;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DispenserBlock;

public class CommonRegister {
    public static void registerConfig() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
    }

    public static void registerEventListeners() {

    }

    public static void registerRecipeSerializers() {
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "color"), ModRecipeSerializers.COLOR);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "upgrade"), ModRecipeSerializers.UPGRADE);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation(ToLaserBlade.MOD_ID, "model_change"), ModRecipeSerializers.MODEL_CHANGE);
    }

    public static void registerItems() {
        // Laser Blades
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade"), ModItems.LASER_BLADE);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "laser_blade_fp"), ModItems.LASER_BLADE_FP);
        // Blueprint
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_blueprint"), ModItems.LB_BLUEPRINT);
        // Laser Blade parts
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_battery"), ModItems.LB_BATTERY);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_medium"), ModItems.LB_MEDIUM);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_emitter"), ModItems.LB_EMITTER);
        Registry.register(Registry.ITEM, new ResourceLocation(ToLaserBlade.MOD_ID, "lb_casing"), ModItems.LB_CASING);
    }

    public static void registerItemGroups() {
        ModCreativeModeTabs.initModCreativeModeTabs();
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

    public static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation(ToLaserBlade.MOD_ID, "item_laser_blade_swing"), ModSoundEvents.ITEM_LASER_BLADE_SWING);
        Registry.register(Registry.SOUND_EVENT, new ResourceLocation(ToLaserBlade.MOD_ID, "item_laser_blade_fp_swing"), ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
    }

    private CommonRegister() {}
}
