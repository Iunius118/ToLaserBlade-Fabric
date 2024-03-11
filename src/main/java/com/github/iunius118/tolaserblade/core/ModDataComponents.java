package com.github.iunius118.tolaserblade.core;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.component.CustomData;

public class ModDataComponents {
    public static final DataComponentType<CustomData> LASER_BLADE = new DataComponentType.Builder<CustomData>().persistent(CustomData.CODEC).build();
}
