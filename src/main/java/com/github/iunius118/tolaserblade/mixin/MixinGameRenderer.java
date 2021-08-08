package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Shadow
    @Final
    private Map<String, ShaderInstance> shaders;

    @Inject(method = "reloadShaders", at = @At("TAIL"))
    private void onReloadShadersEnd(ResourceManager resourceManager, CallbackInfo ci) {
        ShaderInstance shaderInstance = GameRenderer.getRendertypeEntityTranslucentCullShader();

        // Register laser blade shader
        try {
            shaderInstance = new ShaderInstance(resourceManager, LaserBladeRenderType.UNLIT_SHADER_INSTANCE_NAME, DefaultVertexFormat.NEW_ENTITY);
            ToLaserBlade.LOGGER.info("Created: shader " + LaserBladeRenderType.UNLIT_SHADER_INSTANCE_NAME);
        } catch (IOException ignored) {
        }

        shaders.put(LaserBladeRenderType.UNLIT_SHADER_INSTANCE_NAME, shaderInstance);
        LaserBladeRenderType.setUnlitShaderInstance(shaderInstance);
    }
}
