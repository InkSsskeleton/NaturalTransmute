package com.zg.natural_transmute.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.AbstractList;

public class HarmoniousChangeSerializer implements RecipeSerializer<HarmoniousChangeRecipe> {

    private static final MapCodec<HarmoniousChangeRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Ingredient.CODEC.listOf(1, 3).fieldOf("ingredients")
                            .flatXmap(list -> {
                                Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                                return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                                }, DataResult::success)
                            .forGetter(HarmoniousChangeRecipe::getIngredients),
                    ItemStack.STRICT_CODEC.listOf().fieldOf("excepts")
                            .flatXmap(list -> {
                                ItemStack[] itemStacks = list.toArray(ItemStack[]::new);
                                return DataResult.success(NonNullList.of(ItemStack.EMPTY, itemStacks));
                                }, DataResult::success)
                            .forGetter(HarmoniousChangeRecipe::getExcepts),
                    ItemStack.STRICT_CODEC.listOf(1, 3).fieldOf("results")
                            .flatXmap(list -> {
                                ItemStack[] itemStacks = list.toArray(ItemStack[]::new);
                                return DataResult.success(NonNullList.of(ItemStack.EMPTY, itemStacks));
                            }, DataResult::success)
                            .forGetter(HarmoniousChangeRecipe::getResults),
                    Ingredient.CODEC_NONEMPTY.fieldOf("biome_catalysts").forGetter(HarmoniousChangeRecipe::getMetaphysicas),
                    Codec.INT.fieldOf("time").forGetter(HarmoniousChangeRecipe::getTime),
                    Codec.BOOL.fieldOf("consume").forGetter(HarmoniousChangeRecipe::isConsume)
            ).apply(instance, HarmoniousChangeRecipe::new));

    @Override
    public MapCodec<HarmoniousChangeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, HarmoniousChangeRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private HarmoniousChangeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        int ingredientsSize = buffer.readVarInt();
        int exceptsSize = buffer.readVarInt();
        int resultsSize = buffer.readVarInt();

        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsSize, Ingredient.EMPTY);
        NonNullList<ItemStack> excepts = NonNullList.withSize(exceptsSize, ItemStack.EMPTY);
        NonNullList<ItemStack> results = NonNullList.withSize(resultsSize, ItemStack.EMPTY);

        for (int i = 0; i < ingredientsSize; i++) {
            try {
                Ingredient decoded = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                ingredients.set(i, decoded.isEmpty() ? Ingredient.EMPTY : decoded);
            } catch (Exception e) {
                ingredients.set(i, Ingredient.EMPTY);
            }
        }

        for (int i = 0; i < exceptsSize; i++) {
            try {
                ItemStack decoded = ItemStack.STREAM_CODEC.decode(buffer);
                excepts.set(i, decoded.isEmpty() ? ItemStack.EMPTY : decoded);
            } catch (Exception e) {
                excepts.set(i, ItemStack.EMPTY);
            }
        }

        for (int i = 0; i < resultsSize; i++) {
            try {
                ItemStack decoded = ItemStack.STREAM_CODEC.decode(buffer);
                results.set(i, decoded.isEmpty() ? ItemStack.EMPTY : decoded);
            } catch (Exception e) {
                results.set(i, ItemStack.EMPTY);
            }
        }

        Ingredient biome_catalyst = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        if (biome_catalyst.isEmpty()) {
            biome_catalyst = Ingredient.EMPTY;
        }

        return new HarmoniousChangeRecipe(ingredients, excepts, results,
                biome_catalyst, buffer.readVarInt(), buffer.readBoolean());
    }


    private void toNetwork(RegistryFriendlyByteBuf buffer, HarmoniousChangeRecipe recipe) {
        buffer.writeVarInt(recipe.getIngredients().size());
        buffer.writeVarInt(recipe.getExcepts().size());
        buffer.writeVarInt(recipe.getResults().size());
        for (Ingredient ingredient : recipe.getIngredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }

        for (ItemStack stack : recipe.getExcepts()) {
            ItemStack.STREAM_CODEC.encode(buffer, stack);
        }

        for (ItemStack stack : recipe.getResults()) {
            ItemStack.STREAM_CODEC.encode(buffer, stack);
        }

        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getMetaphysicas());
        buffer.writeVarInt(recipe.getTime());
        buffer.writeBoolean(recipe.isConsume());
    }

}