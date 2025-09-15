package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.fabricmc.api.ClientModInitializer;

public class ToLaserBladeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegister.registerNetworking();
        ClientRegister.registerTintSources();
        ClientRegister.registerModelLoadingPlugin();
        ClientRegister.registerConditionalItemModelProperties();
        ClientRegister.registerSpecialModelRenderers();
        ClientRegister.registerParticleProviders();
        ClientRegister.registerResourcePacks();
        ClientRegister.registerItemTooltip();

        // Register event listener to register laser blade models
        ToLaserBladeAPI.registerModelRegistrationListener(register -> register.accept(LaserBladeModelManager.loadModels()));
    }
}
