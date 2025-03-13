package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LaserBladeModelLoadingPlugin;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.data.TLBSampleSoundPackProvider;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.core.Direction;

import java.util.Optional;

public class ClientRegister {
    public static void registerTintSources() {
        ItemTintSources.ID_MAPPER.put(ToLaserBlade.makeId("laser_blade"), LaserBladeTintSource.MAP_CODEC);
    }

    public static void registerModelLoadingPlugin() {
        ModelLoadingPlugin.register(new LaserBladeModelLoadingPlugin());
    }

    public static void registerSpecialModelRenderers() {
        SpecialModelRenderers.ID_MAPPER.put(ToLaserBlade.makeId("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    public static void registerParticleProviders() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    public static void registerResourcePacks() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(ToLaserBlade.MOD_ID);
        container.ifPresent(TLBSampleSoundPackProvider::addResourcePack);
    }

    private ClientRegister() {}
}
