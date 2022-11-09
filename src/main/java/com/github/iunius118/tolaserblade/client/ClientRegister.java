package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.client.color.item.LBCasingItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBEmitterItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBMediumItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBSwordItemColor;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LaserBladeModelProvider;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.core.Direction;

public class ClientRegister {
    public static void registerColorProvider() {
        // Laser Blades
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE);
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE_FP);
        // Laser Blade Parts
        ColorProviderRegistry.ITEM.register(new LBMediumItemColor(), ModItems.LB_MEDIUM);
        ColorProviderRegistry.ITEM.register(new LBEmitterItemColor(), ModItems.LB_EMITTER);
        ColorProviderRegistry.ITEM.register(new LBCasingItemColor(), ModItems.LB_CASING);
    }

    public static void registerResourceProvider() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new LaserBladeModelProvider());
    }

    public static void registerItemRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LASER_BLADE, new LBSwordItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LASER_BLADE_FP, new LBSwordItemRenderer());
    }

    public static void registerParticleProviders() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    private ClientRegister() {}
}
