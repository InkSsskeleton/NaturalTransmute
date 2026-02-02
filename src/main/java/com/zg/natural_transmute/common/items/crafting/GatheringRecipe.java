package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.registry.NTRecipeSerializers;
import com.zg.natural_transmute.registry.NTRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class GatheringRecipe implements Recipe<GatheringRecipeInput> {

    protected final Ingredient input1;
    protected final Ingredient input2;
    protected final ItemStack core;
    public final ItemStack result;
    public int gatheringTime;

    public GatheringRecipe(
            Ingredient input1, Ingredient input2, ItemStack core,
            ItemStack result, int gatheringTime) {
        this.input1 = input1;
        this.input2 = input2;
        this.core = core;
        this.result = result;
        this.gatheringTime = gatheringTime;
    }

    @Override
    public boolean matches(GatheringRecipeInput input, Level level) {
        ItemStack item1 = input.getItem(0);
        ItemStack item2 = input.getItem(1);

        boolean flag1 = this.input1.test(item1) && this.input2.test(item2);
        boolean flag2 = this.input1.test(item2) && this.input2.test(item1);
        return (flag1 || flag2) && this.core.is(input.getItem(2).getItem());
    }

    @Override
    public ItemStack assemble(GatheringRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.GATHERING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NTRecipes.GATHERING_RECIPE.get();
    }

}