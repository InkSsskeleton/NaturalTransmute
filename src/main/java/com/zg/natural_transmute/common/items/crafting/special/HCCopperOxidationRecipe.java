package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.List;

public class HCCopperOxidationRecipe extends HarmoniousChangeRecipe {

    public HCCopperOxidationRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    protected boolean extraMatches(HarmoniousChangeRecipeInput input) {
        return Block.byItem(input.getItem(0).getItem()) instanceof WeatheringCopper;
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        Block block = Block.byItem(input.getItem(0).getItem());
        return new ItemStack(WeatheringCopper.getNext(block).orElse(block));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            if (Block.byItem(item) instanceof WeatheringCopper) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_SWAMP.get(), NTItems.H_MANGROVE_SWAMP.get(),
                NTItems.H_JUNGLE.get(), NTItems.H_RIVER.get(), NTItems.H_OCEAN.get(),
                NTItems.H_FROZEN_OCEAN.get(), NTItems.H_BEACH.get(), NTItems.H_STONE_SHORE.get(),
                NTItems.H_WARM_OCEAN.get(), NTItems.H_LUSH_CAVE.get(), NTItems.H_DRIPSTONE_CAVES.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_COPPER_OXIDATION_SERIALIZER.get();
    }

}