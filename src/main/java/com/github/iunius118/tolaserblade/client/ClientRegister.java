package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.client.color.item.LBCasingItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBEmitterItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBMediumItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBSwordItemColor;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LaserBladeModelLoadingPlugin;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.integration.autoconfig.ModConfig;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;

import java.util.List;

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

    public static void registerModelLoadingPlugin() {
        ModelLoadingPlugin.register(new LaserBladeModelLoadingPlugin());
    }

    public static void registerItemRenderer() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        List<Item> laserBladeItems = config.getLaserBladeItems();
        laserBladeItems.forEach(item -> BuiltinItemRendererRegistry.INSTANCE.register(item, new LBSwordItemRenderer()));
    }

    public static void registerParticleProviders() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    private ClientRegister() {}
}
