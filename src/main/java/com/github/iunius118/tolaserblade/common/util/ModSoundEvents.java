package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent ITEM_LASER_BLADE_SWING = register("item.laser_blade.swing");
    public static final SoundEvent ITEM_LASER_BLADE_FP_SWING = register("item.laser_blade_fp.swing");
    public static final SoundEvent ITEM_LASER_BLADE_HIT = register("item.laser_blade.hit");
    public static final SoundEvent ITEM_LASER_BLADE_FP_HIT = register("item.laser_blade_fp.hit");
    public static final Holder.Reference<SoundEvent> ITEM_LASER_BLADE_BLOCK = registerForHolder("item.laser_blade.block");
    public static final Holder.Reference<SoundEvent> ITEM_LASER_BLADE_FP_BLOCK = registerForHolder("item.laser_blade_fp.block");
    public static final SoundEvent ITEM_LASER_TRAP_ACTIVATE = register("item.laser_trap.activate");
    /*// Only for Forge/NeoForge versions
    public static final SoundEvent ITEM_DX_LASER_BLADE_SWING = register("item.dx_laser_blade.swing");
    public static final SoundEvent ITEM_DX_LASER_BLADE_HIT = register("item.dx_laser_blade.hit");
    public static final SoundEvent ITEM_LB_BRAND_NEW_USE = register("item.lb_brand_new.use");
    public static final SoundEvent ITEM_LB_BRAND_NEW_FP_USE = register("item.lb_brand_new_fp.use");
    //*/

    public static SoundEvent register(String name) {
        ResourceLocation id = ToLaserBlade.makeId(name);
        var soundEvent = SoundEvent.createVariableRangeEvent(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, soundEvent);
    }

    public static Holder.Reference<SoundEvent> registerForHolder(String name) {
        ResourceLocation id = ToLaserBlade.makeId(name);
        var soundEvent = SoundEvent.createVariableRangeEvent(ToLaserBlade.makeId(name));
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, soundEvent);
    }

    public static void register() {
        ToLaserBlade.LOGGER.debug("Register mod sound events");
    }
}
