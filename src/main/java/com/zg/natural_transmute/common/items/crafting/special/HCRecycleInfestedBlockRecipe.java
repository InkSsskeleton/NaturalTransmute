package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;

import java.util.List;

public class HCRecycleInfestedBlockRecipe extends HarmoniousChangeRecipe {

    public HCRecycleInfestedBlockRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            if (Block.byItem(item) instanceof InfestedBlock) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_SNOWY_SLOPES.get(),
                NTItems.H_MEADOW.get(), NTItems.H_CHERRY_GROVE.get());
    }

    @Override
    public NonNullList<ItemStack> getResults() {
        return NonNullList.of(new ItemStack(Items.GRAVEL), new ItemStack(NTItems.SILVERFISH_PUPA));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_RECYCLE_INFESTED_BLOCK_SERIALIZER.get();
    }

}