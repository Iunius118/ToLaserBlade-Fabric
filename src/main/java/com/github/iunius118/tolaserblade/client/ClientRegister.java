package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.client.color.LBSwordItemColor;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ClientRegister {
    private ClientRegister() {}

    public static void registerColorProvider() {
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE);
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE_FP);
    }
}
