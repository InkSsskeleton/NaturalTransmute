package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import com.zg.natural_transmute.registry.NTRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

public class HarmoniousChangeRecipe implements Recipe<HarmoniousChangeRecipeInput> {

    protected final NonNullList<Ingredient> ingredients;
    private final NonNullList<ItemStack> excepts;
    private final NonNullList<ItemStack> results;
    private final Ingredient biome_catalyst;
    private final int time;
    private final boolean consume;

    public HarmoniousChangeRecipe(
            NonNullList<Ingredient> ingredients,
            NonNullList<ItemStack> excepts,
            NonNullList<ItemStack> results,
            Ingredient biome_catalyst,
            int time, boolean consume) {
        this.ingredients = ingredients;
        this.excepts = excepts;
        this.results = results;
        this.biome_catalyst = biome_catalyst;
        this.time = time;
        this.consume = consume;
    }

    public HarmoniousChangeRecipe(NonNullList<Ingredient> ingredients, NonNullList<ItemStack> results, Ingredient biome_catalyst) {
        this(ingredients, NonNullList.create(), results, biome_catalyst, 160, Boolean.TRUE);
    }

    protected boolean extraMatches(HarmoniousChangeRecipeInput input) {
        return input.size() == 1 && this.getIngredients().size() == 1
                ? this.getIngredients().getFirst().test(input.getItem(0))
                : input.stackedContents().canCraft(this, (null));
    }

    @Override
    public boolean matches(HarmoniousChangeRecipeInput input, Level level) {
        if (input.ingredientCount() != this.getIngredients().size()) {
            return false;
        } else {
            Item item = input.getItem(4).getItem();
            boolean flag1 = HarmoniousChangeStoveBlockEntity.getFuel().containsKey(input.getItem(3).getItem());
            boolean flag2 = this.getMetaphysicas().test(input.getItem(4));
            boolean flag3 = item.components().has(NTDataComponents.ASSOCIATED_BIOMES.get());
            return flag1 && flag2 && flag3 && this.extraMatches(input);
        }
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        return this.getResultItem(registries).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > this.getIngredients().size();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.getResults().getFirst();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        if (!this.excepts.isEmpty()) {
            NonNullList<Ingredient> newIngredients = NonNullList.create();
            for (Ingredient ingredient : this.ingredients) {
                ItemStack[] items = ingredient.getItems();
                List<ItemStack> list = Arrays.asList(items);
                list.removeAll(this.excepts);
                newIngredients.add(Ingredient.of(list.stream()));
            }

            return newIngredients;
        } else {
            return this.ingredients;
        }
    }

    public NonNullList<ItemStack> getExcepts() {
        return this.excepts;
    }

    public NonNullList<ItemStack> getResults() {
        return this.results;
    }

    public Ingredient getMetaphysicas() {
        return this.biome_catalyst;
    }

    public int getTime() {
        return this.time;
    }

    public boolean isConsume() {
        return this.consume;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HARMONIOUS_CHANGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NTRecipes.HARMONIOUS_CHANGE_RECIPE.get();
    }

}