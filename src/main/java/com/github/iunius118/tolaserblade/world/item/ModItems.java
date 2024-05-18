package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.world.item.Item;

public class ModItems {
    // Laser Blades
    public static final LBSwordItem LASER_BLADE = new LBSwordItem(false);
    public static final LBSwordItem LASER_BLADE_FP = new LBSwordItem(true);
    // Blueprint
    public static final Item LB_BLUEPRINT = LBBlueprintItem.getInstance();
    // Laser Blade parts
    public static final Item LB_BATTERY = new LBPartItem(Upgrade.Type.BATTERY);
    public static final Item LB_MEDIUM = new LBPartItem(Upgrade.Type.MEDIUM);
    public static final Item LB_EMITTER = new LBPartItem(Upgrade.Type.EMITTER);
    public static final Item LB_CASING = new LBPartItem(Upgrade.Type.CASING);
}
