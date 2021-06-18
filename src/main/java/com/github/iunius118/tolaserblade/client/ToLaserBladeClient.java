package com.github.iunius118.tolaserblade.client;

import net.fabricmc.api.ClientModInitializer;

public class ToLaserBladeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegister.registerColorProvider();
        ClientRegister.registerResourceProvider();
        ClientRegister.registerItemRenderer();
    }
}
