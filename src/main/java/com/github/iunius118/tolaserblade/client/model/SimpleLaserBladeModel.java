package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import net.minecraft.client.renderer.RenderType;

public abstract class SimpleLaserBladeModel extends SimpleModel implements LaserBladeModel {
    private RenderType typeHilt;
    private RenderType typeUnlit;
    private RenderType typeAdd;
    private RenderType typeSubInner;
    private RenderType typeSub;

    public RenderType getHiltRenderType() {
        if (typeHilt == null) {
            var renderTypeOpt = LaserBladeRenderType.getRenderType("hilt", LaserBladeRenderType.getHiltRenderState(getTexture()));
            typeHilt = renderTypeOpt.orElse(RenderType.entityTranslucent(getTexture()));
        }

        return typeHilt;
    }

    public RenderType getUnlitRenderType() {
        if (typeUnlit == null) {
            var renderTypeOpt = LaserBladeRenderType.getRenderType("laser_unlit", LaserBladeRenderType.getUnlitRenderState(getTexture()));
            typeUnlit = renderTypeOpt.orElse(RenderType.entityTranslucent(getTexture()));
        }

        return typeUnlit;
    }

    public RenderType getAddRenderType() {
        if (typeAdd == null) {
            var renderTypeOpt = LaserBladeRenderType.getRenderType("laser_add", LaserBladeRenderType.getAddRenderState(getTexture()));
            typeAdd = renderTypeOpt.orElse(RenderType.entityTranslucent(getTexture()));
        }

        return typeAdd;
    }

    public RenderType getSubInnerRenderType() {
        if (typeSubInner == null) {
            var renderTypeOpt = LaserBladeRenderType.getRenderType("laser_sub_in", LaserBladeRenderType.getSubRenderState(getTexture()));
            typeSubInner = renderTypeOpt.orElse(RenderType.entityTranslucent(getTexture()));
        }

        return typeSubInner;
    }

    public RenderType getSubRenderType() {
        if (typeSub == null) {
            var renderTypeOpt = LaserBladeRenderType.getRenderType("laser_sub", LaserBladeRenderType.getSubRenderState(getTexture()));
            typeSub = renderTypeOpt.orElse(RenderType.entityTranslucent(getTexture()));
        }

        return typeSub;
    }

    public RenderType getInnerBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubInnerRenderType() : getAddRenderType();
    }

    public RenderType getOuterBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubRenderType() : getAddRenderType();
    }
}
