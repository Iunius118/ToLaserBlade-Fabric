package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticleGroup;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LaserBladeModelLoadingPlugin;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.Blocking;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.data.TLBSampleSoundPackProvider;
import com.github.iunius118.tolaserblade.network.SyncConfigCompleteC2SPayload;
import com.github.iunius118.tolaserblade.network.SyncConfigS2CPayload;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemBase;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.core.Direction;

import java.util.Optional;

public class ClientRegister {

    public static void registerNetworking() {
        // Register receiver to handle the config packet from server
        ClientConfigurationNetworking.registerGlobalReceiver(SyncConfigS2CPayload.TYPE, (packet, context) -> {
            // Handle the received packet
            // Apply server-side config to client
            ToLaserBlade.serverConfig = packet.serverConfig();
            ToLaserBlade.LOGGER.info("[ToLaserBlade] Received server-side config (SyncConfigS2CPayload) from server: {}", ToLaserBlade.serverConfig);

            // Respond back to the server that the config task is complete
            context.responseSender().sendPacket(SyncConfigCompleteC2SPayload.INSTANCE);
        });
    }

    public static void registerTintSources() {
        ItemTintSources.ID_MAPPER.put(ToLaserBlade.makeId("laser_blade"), LaserBladeTintSource.MAP_CODEC);
    }

    public static void registerConditionalItemModelProperties() {
        ConditionalItemModelProperties.ID_MAPPER.put(ToLaserBlade.makeId("blocking"), Blocking.MAP_CODEC);
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
        ParticleRendererRegistry.register(LaserTrapParticle.PARTICLE_RENDER_TYPE, LaserTrapParticleGroup::new);
    }

    public static void registerResourcePacks() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(ToLaserBlade.MOD_ID);
        container.ifPresent(TLBSampleSoundPackProvider::addResourcePack);
    }

    public static void registerItemTooltip() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.getItem() instanceof LaserBladeItemBase item) {
                item.appendTooltip(itemStack, tooltipContext, tooltipType, list);
            }
        });
    }

    private ClientRegister() {}
}
