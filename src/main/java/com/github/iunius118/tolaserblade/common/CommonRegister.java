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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.DispenserBlock;

public class CommonRegister {
    public static void registerConfig() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
    }

    public static void registerEventListeners() {

    }

    public static void registerRecipeSerializers() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("color"), ModRecipeSerializers.COLOR);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("upgrade"), ModRecipeSerializers.UPGRADE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("model_change"), ModRecipeSerializers.MODEL_CHANGE);
    }

    public static void registerItems() {
        // Laser Blades
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("laser_blade"), ModItems.LASER_BLADE);
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("laser_blade_fp"), ModItems.LASER_BLADE_FP);
        // Blueprint
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("lb_blueprint"), ModItems.LB_BLUEPRINT);
        // Laser Blade parts
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("lb_battery"), ModItems.LB_BATTERY);
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("lb_medium"), ModItems.LB_MEDIUM);
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("lb_emitter"), ModItems.LB_EMITTER);
        Registry.register(BuiltInRegistries.ITEM, ToLaserBlade.makeId("lb_casing"), ModItems.LB_CASING);
    }

    public static void registerItemGroups() {
        ModCreativeModeTabs.initModCreativeModeTabs();
    }

    public static void registerEnchantments() {
        Registry.register(BuiltInRegistries.ENCHANTMENT, ToLaserBlade.makeId("light_element"), ModEnchantments.LIGHT_ELEMENT);
    }

    public static void registerDispenseItemBehaviors() {
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE, new DispenseLBSwordBehavior());
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE_FP, new DispenseLBSwordBehavior());
    }

    public static void registerParticleTypes() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_x"), ModParticleTypes.LASER_TRAP_X);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_y"), ModParticleTypes.LASER_TRAP_Y);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_z"), ModParticleTypes.LASER_TRAP_Z);
    }

    public static void registerSoundEvents() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, ToLaserBlade.makeId("item_laser_blade_swing"), ModSoundEvents.ITEM_LASER_BLADE_SWING);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ToLaserBlade.makeId("item_laser_blade_fp_swing"), ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
    }

    private CommonRegister() {}
}
