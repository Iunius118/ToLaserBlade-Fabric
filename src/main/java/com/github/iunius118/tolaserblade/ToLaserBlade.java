package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.common.CommonRegister;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToLaserBlade implements ModInitializer {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        CommonRegister.registerConfig();
        CommonRegister.registerEventListeners();
        CommonRegister.registerRecipeSerializers();
        CommonRegister.registerItems();
        CommonRegister.registerItemGroups();
        CommonRegister.registerEnchantments();
        CommonRegister.registerDispenseItemBehaviors();
        CommonRegister.registerParticleTypes();
        CommonRegister.registerSoundEvents();
        CommonRegister.registerDataComponentTypes();
    }
    
    public static ResourceLocation makeId(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
