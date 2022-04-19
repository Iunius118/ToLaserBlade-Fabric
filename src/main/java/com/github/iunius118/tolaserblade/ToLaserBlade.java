package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.common.CommonRegister;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ToLaserBlade implements ModInitializer {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        CommonRegister.registerConfig();
        CommonRegister.registerEventListeners();
        CommonRegister.registerRecipeSerializers();
        CommonRegister.registerItems();
        CommonRegister.registerEnchantments();
        CommonRegister.registerDispenseItemBehaviors();
        CommonRegister.registerParticleTypes();
        CommonRegister.registerSoundEvents();
    }
}
