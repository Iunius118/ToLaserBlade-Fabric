package com.github.iunius118.tolaserblade.world.item.crafting;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeDataWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class LBModelChangeRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int type;
    private ItemStack sample;

    public LBModelChangeRecipe(Ingredient template, Ingredient base, Ingredient addition, int type) {
        super(template, base, addition, getResultItemStack(base));
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.type = type;
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
        int baseType = LaserBlade.of(baseStack).getType();
        return type >= 0 && baseType != type;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack baseStack = container.getItem(SmithingMenu.BASE_SLOT);
        ItemStack itemstack = baseStack.copy();
        return getResult(itemstack);
    }

    private ItemStack getResult(ItemStack input) {
        LaserBladeDataWriter.of(input).modelType(type).write();
        return input;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        if (sample != null) {
            return sample;
        }

        ItemStack output = super.getResultItem(registryAccess);

        if (output.isEmpty()) {
            sample = ItemStack.EMPTY;
            return sample;
        }

        sample = getResult(output.copy());
        return sample;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MODEL_CHANGE;
    }

    public static class Serializer implements RecipeSerializer<LBModelChangeRecipe> {
        private static final Codec<LBModelChangeRecipe> CODEC = RecordCodecBuilder.create(
                (instance) -> instance.group(
                        Ingredient.CODEC.fieldOf("template").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.template),
                        Ingredient.CODEC.fieldOf("base").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.base),
                        Ingredient.CODEC.fieldOf("addition").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.addition),
                        Codec.INT.fieldOf("model_type").codec().fieldOf("result").forGetter(lBModelChangeRecipe -> lBModelChangeRecipe.type)
                ).apply(instance, LBModelChangeRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, LBModelChangeRecipe> STREAM_CODEC = StreamCodec.of(LBModelChangeRecipe.Serializer::toNetwork, LBModelChangeRecipe.Serializer::fromNetwork);

        @Override
        public Codec<LBModelChangeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LBModelChangeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static LBModelChangeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            int type = buf.readInt();
            return new LBModelChangeRecipe(template, base, addition, type);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, LBModelChangeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.addition);
            buf.writeInt(recipe.type);
        }
    }
}
