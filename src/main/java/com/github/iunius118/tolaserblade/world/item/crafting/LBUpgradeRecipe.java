package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeManager;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class LBUpgradeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final ResourceLocation upgradeId;
    private Upgrade upgrade;
    private ItemStack sample;

    public LBUpgradeRecipe(Ingredient template, Ingredient base, Ingredient addition, ResourceLocation upgradeId) {
        super(template, base, addition, getResultItemStack(base));
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.upgradeId = upgradeId;
    }

    private static ItemStack getResultItemStack(Ingredient base) {
        ItemStack[] matchingStacks = base.getItems();

        if (matchingStacks.length > 0 && matchingStacks[0] != null) {
            return matchingStacks[0].copy();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!super.matches(container, level)) {
            return false;
        }

        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack additionalStack = container.getItem(SmithingMenu.ADDITIONAL_SLOT);
        Upgrade upgrade = getUpgrade();
        return upgrade.canApply(baseStack, additionalStack);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getUpgradingResult(itemstack);
    }

    private ItemStack getUpgradingResult(ItemStack input) {
        Upgrade upgrade = getUpgrade();
        UpgradeResult result = upgrade.apply(input, 0);
        return result.itemStack();
    }

    private Upgrade getUpgrade() {
        if (upgrade != null) {
            return upgrade;
        }

        upgrade = UpgradeManager.getUpgrade(upgradeId);
        return upgrade;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(registryAccess);
        sample = output.copy();

        if (sample.isEmpty()) {
            return sample;
        }

        ResourceLocation efficiencyRemover = UpgradeID.EFFICIENCY_REMOVER.getID();

        if (upgradeId.equals(efficiencyRemover)) {
            // Set hint of removing Efficiency to item-stack's display name
            MutableComponent componentContents = LaserBladeTextKey.KEY_TOOLTIP_REMOVE.translate(Component.translatable("enchantment.minecraft.efficiency"));
            MutableComponent info = Component.literal(componentContents.getString());
            CompoundTag nbt = sample.getOrCreateTagElement("display");
            nbt.putString("Name", Component.Serializer.toJson(info));
        } else {
            // Apply upgrade to sample item
            sample = getUpgradingResult(output);
        }

        return sample;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UPGRADE;
    }

    public static class Serializer implements RecipeSerializer<LBUpgradeRecipe> {
        private static final Codec<LBUpgradeRecipe> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.addition),
                        ResourceLocation.CODEC.fieldOf("type").codec().fieldOf("result").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.upgradeId)
                ).apply(instance, LBUpgradeRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, LBUpgradeRecipe> STREAM_CODEC = StreamCodec.of(LBUpgradeRecipe.Serializer::toNetwork, LBUpgradeRecipe.Serializer::fromNetwork);

        @Override
        public Codec<LBUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LBUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }


        private static LBUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ResourceLocation upgradeId = buf.readResourceLocation();
            return new LBUpgradeRecipe(template, base, addition, upgradeId);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, LBUpgradeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.addition);
            buf.writeResourceLocation(recipe.upgradeId);
        }
    }
}
