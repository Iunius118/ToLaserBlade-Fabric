package com.github.iunius118.tolaserblade.data.lang;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.tags.ModEntityTypeTags;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class EnglishTranslationProvider extends FabricLanguageProvider {
    public EnglishTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        // Item group
        translationBuilder.add(LaserBladeTextKey.KEY_ITEM_GROUP_GENERAL.getKey(), ToLaserBlade.MOD_NAME);

        // Items
        translationBuilder.add(ModItems.LASER_BLADE, "Laser Blade");
        translationBuilder.add(ModItems.LASER_BLADE_FP, "Laser Blade");
        translationBuilder.add(ModItems.LB_BLUEPRINT, "Laser Blade Blueprint");
        translationBuilder.add(ModItems.LB_BATTERY, "LB Energy Cell");
        translationBuilder.add(ModItems.LB_MEDIUM, "Laser Medium");
        translationBuilder.add(ModItems.LB_EMITTER, "Laser Blade Emitter");
        translationBuilder.add(ModItems.LB_CASING, "Laser Blade Casing");

        // Enchantments
        addEnchantment(translationBuilder, ModEnchantments.LIGHT_ELEMENT, "Light Element",
                "Increases damage from Laser Blade, and deals additional damage to undead mobs and illagers.");

        // Item tooltip
        translationBuilder.add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.getKey(), "Model %s");

        // Upgrade tooltip
        translationBuilder.add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), "Laser Power %s");
        translationBuilder.add(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), "Recharge %s");
        translationBuilder.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.getKey(), "HEAT RESISTANT 8000");

        // Tags
        translationBuilder.add(ModEntityTypeTags.SENSITIVE_TO_LIGHT_ELEMENT, "Sensitive to Light Element");
        translationBuilder.add(ModItemTags.ATTACK_SPEED_UPGRADE, "Ingredients to Upgrade Attack Speed of Laser Blade");
        translationBuilder.add(ModItemTags.EFFICIENCY_UPGRADE, "Ingredients to Upgrade Efficiency of Laser Blade");
        translationBuilder.add(ModItemTags.EFFICIENCY_REMOVER, "Ingredients to Remove Efficiency from Laser Blade");
        translationBuilder.add(ModItemTags.ATTACK_DAMAGE_UPGRADE, "Ingredients to Upgrade Attack Damage of Laser Blade");
        translationBuilder.add(ModItemTags.LIGHT_ELEMENT_UPGRADE, "Ingredients to Upgrade Light Element of Laser Blade");
        translationBuilder.add(ModItemTags.FIRE_ASPECT_UPGRADE, "Ingredients to Upgrade Fire Aspect of Laser Blade");
        translationBuilder.add(ModItemTags.SWEEPING_EDGE_UPGRADE, "Ingredients to Upgrade Sweeping Edge of Laser Blade");
        translationBuilder.add(ModItemTags.SILK_TOUCH_UPGRADE, "Ingredients to Upgrade Silk Touch of Laser Blade");
        translationBuilder.add(ModItemTags.LOOTING_UPGRADE, "Ingredients to Upgrade Looting of Laser Blade");
        translationBuilder.add(ModItemTags.MENDING_UPGRADE, "Ingredients to Upgrade Mending of Laser Blade");
        translationBuilder.add(ModItemTags.CASING_REPAIR, "Ingredients to Repair Casing of Laser Blade");
    }

    public void addEnchantment(TranslationBuilder translationBuilder, Enchantment enchantment, String name, String description) {
        // Register enchantment name
        translationBuilder.add(enchantment, name);

        // Support for Enchantment Descriptions mod
        String id = BuiltInRegistries.ENCHANTMENT.getKey(enchantment).toString().replace(':', '.');
        translationBuilder.add("enchantment." + id + ".desc", description);
    }
}
