package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.api.client.event.ToLaserBladeClientEvent;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.fabricmc.api.ClientModInitializer;

public class ToLaserBladeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegister.registerColorProvider();
        ClientRegister.registerResourceProvider();
        ClientRegister.registerItemRenderer();
        ClientRegister.registerParticleProviders();

        // Register event listener to register laser blade models
        ToLaserBladeClientEvent.REGISTER_MODEL.register(register -> register.accept(LaserBladeModelManager.loadModels()));
    }
}
