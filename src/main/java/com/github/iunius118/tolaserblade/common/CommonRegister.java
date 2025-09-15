package com.github.iunius118.tolaserblade.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLBSwordBehavior;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.integration.autoconfig.TLBLocalConfig;
import com.github.iunius118.tolaserblade.network.SyncConfigCompleteC2SPayload;
import com.github.iunius118.tolaserblade.network.SyncConfigS2CPayload;
import com.github.iunius118.tolaserblade.network.SyncConfigTask;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.crafting.ModRecipeSerializers;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.DispenserBlock;

public class CommonRegister {
    public static void registerConfig() {
        AutoConfig.register(TLBLocalConfig.class, Toml4jConfigSerializer::new);
    }

    public static void registerNetworking() {
        // Register payload types
        PayloadTypeRegistry.configurationS2C().register(SyncConfigS2CPayload.TYPE, SyncConfigS2CPayload.CODEC);
        PayloadTypeRegistry.configurationC2S().register(SyncConfigCompleteC2SPayload.TYPE, SyncConfigCompleteC2SPayload.CODEC);

        // Register event to add config task to send server config to client
        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            if (ServerConfigurationNetworking.isReconfiguring(handler)) {
                ToLaserBlade.LOGGER.info("Reconfiguring client");
            }

            if (ServerConfigurationNetworking.canSend(handler, SyncConfigS2CPayload.TYPE)) {
                // Add a config task if the client can receive the config packet
                handler.addTask(new SyncConfigTask(ToLaserBlade.serverConfig));
                ToLaserBlade.LOGGER.info("Add SyncConfigTask to ServerConfigurationPacketListener");
            } else {
                // You can opt to disconnect the client if it cannot handle the config task
                handler.disconnect(Component.literal("ToLaserBlade sync config packet is not supported by client"));
            }
        });

        // Register receiver to handle the completion notification from client and complete the config task
        ServerConfigurationNetworking.registerGlobalReceiver(SyncConfigCompleteC2SPayload.TYPE, (packet, context) -> {
            context.networkHandler().completeTask(SyncConfigTask.TYPE);
            ToLaserBlade.LOGGER.info("Received SyncConfigCompleteC2SPayload from client");
        });
    }

    public static void registerEventListeners() {
        AttackEntityCallback.EVENT.register(LaserBladeItemUtil::onPlayerAttackEntity);
    }

    public static void registerGameObjects() {
        CommonRegister.registerRecipeSerializers();
        CommonRegister.registerItems();
        CommonRegister.registerItemGroups();
        CommonRegister.registerDispenseItemBehaviors();
        CommonRegister.registerParticleTypes();
        CommonRegister.registerSoundEvents();
        CommonRegister.registerDataComponentTypes();
    }

    private static void registerRecipeSerializers() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("color"), ModRecipeSerializers.COLOR);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("upgrade"), ModRecipeSerializers.UPGRADE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ToLaserBlade.makeId("model_change"), ModRecipeSerializers.MODEL_CHANGE);
    }

    private static void registerItems() {
        ModItems.register();
    }

    private static void registerItemGroups() {
        ModCreativeModeTabs.initModCreativeModeTabs();
    }

    private static void registerDispenseItemBehaviors() {
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE, new DispenseLBSwordBehavior());
        DispenserBlock.registerBehavior(ModItems.LASER_BLADE_FP, new DispenseLBSwordBehavior());
    }

    private static void registerParticleTypes() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_x"), ModParticleTypes.LASER_TRAP_X);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_y"), ModParticleTypes.LASER_TRAP_Y);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ToLaserBlade.makeId("laser_trap_z"), ModParticleTypes.LASER_TRAP_Z);
    }

    private static void registerSoundEvents() {
        ModSoundEvents.register();
    }

    private static void registerDataComponentTypes() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ToLaserBlade.makeId("lb_atk"), ModDataComponents.LASER_BLADE_ATTACK);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ToLaserBlade.makeId("lb_spd"), ModDataComponents.LASER_BLADE_SPEED);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ToLaserBlade.makeId("lb_mdl"), ModDataComponents.LASER_BLADE_MODEL);
    }

    private CommonRegister() {}
}
