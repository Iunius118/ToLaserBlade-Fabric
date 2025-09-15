package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.common.CommonRegister;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToLaserBlade implements ModInitializer {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static TLBServerConfig serverConfig = TLBServerConfig.DEFAULT;

    @Override
    public void onInitialize() {
        CommonRegister.registerNetworking();
        CommonRegister.registerConfig();
        CommonRegister.registerEventListeners();
        CommonRegister.registerGameObjects();
    }
    
    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
