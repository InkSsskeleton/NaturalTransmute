package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

public class HCCopperDerustingRecipe extends HCCopperOxidationRecipe {

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        Block block = Block.byItem(input.getItem(0).getItem());
        return new ItemStack(WeatheringCopper.getFirst(block));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_WINDSWEPT.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_COPPER_DERUSTING_SERIALIZER.get();
    }

}