package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class ModItems {
    // Laser Blades
    public static final Item LASER_BLADE = registerLBSwordItem("laser_blade", false);
    public static final Item LASER_BLADE_FP = registerLBSwordItem("laser_blade_fp", true);
    // Blueprint
    public static final Item LB_BLUEPRINT = Items.registerItem(modItemId("lb_blueprint"), LBBlueprintItem::new);
    // Laser Blade parts
    public static final Item LB_BATTERY = registerLBPartItem("lb_battery", Upgrade.Type.BATTERY, false);
    public static final Item LB_MEDIUM = registerLBPartItem("lb_medium", Upgrade.Type.MEDIUM, false);
    public static final Item LB_EMITTER = registerLBPartItem("lb_emitter", Upgrade.Type.EMITTER, false);
    public static final Item LB_CASING = registerLBPartItem("lb_casing", Upgrade.Type.CASING, false);

    private static ResourceKey<Item> modItemId(String name) {
        return ResourceKey.create(Registries.ITEM, ToLaserBlade.makeId(name));
    }

    private static Item registerLBSwordItem(String name, boolean isFireResistant) {
        return Items.registerItem(modItemId(name), p -> new LBSwordItem(p, isFireResistant),
                isFireResistant ? new Item.Properties().fireResistant() : new Item.Properties());
    }

    private static Item registerLBPartItem(String name, Upgrade.Type type, boolean isFireResistant) {
        return Items.registerItem(modItemId(name), p -> new LBPartItem(p, type),
                isFireResistant ? new Item.Properties().fireResistant() : new Item.Properties());
    }

    public static void register() {
        ToLaserBlade.LOGGER.debug("Register mod items");
    }
}
