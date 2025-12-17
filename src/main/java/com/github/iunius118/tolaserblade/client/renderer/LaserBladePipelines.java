package com.github.iunius118.tolaserblade.client.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;

public class LaserBladePipelines {
    public static final RenderPipeline TRANSLUCENT = RenderPipelines.register(
            // Use the entity snippet
            RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                    .withLocation("pipeline/tlb_translucent")
                    .withShaderDefine("ALPHA_CUTOUT", 0.1F)
                    .withSampler("Sampler1")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .build()
    );
    // Unlit item render pipeline
    public static final RenderPipeline UNLIT_TRANSLUCENT = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                    .withLocation("pipeline/tlb_unlit_translucent")
                    .withShaderDefine("NO_CARDINAL_LIGHTING")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .build()
    );
    public static final RenderPipeline ADD = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                    .withLocation("pipeline/tlb_add")
                    .withShaderDefine("NO_CARDINAL_LIGHTING")
                    // Use the lightning blend function (additive and alpha)
                    .withBlend(BlendFunction.LIGHTNING)
                    .build()
    );
    public static final RenderPipeline SUB = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                    .withLocation("pipeline/tlb_sub")
                    .withShaderDefine("NO_CARDINAL_LIGHTING")
                    // Use the lightning blend function (additive and alpha)
                    .withBlend(BlendFunction.LIGHTNING)
                    .build()
    );

    private LaserBladePipelines() {}
}
