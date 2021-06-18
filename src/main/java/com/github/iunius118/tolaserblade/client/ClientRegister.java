package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.client.color.LBSwordItemColor;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LaserBladeModelProvider;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ClientRegister {
    public static void registerColorProvider() {
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE);
        ColorProviderRegistry.ITEM.register(new LBSwordItemColor(), ModItems.LASER_BLADE_FP);
    }

    public static void registerResourceProvider() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new LaserBladeModelProvider());
    }

    public static void registerItemRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LASER_BLADE, new LBSwordItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.LASER_BLADE_FP, new LBSwordItemRenderer());
    }

    private ClientRegister() {}
}
