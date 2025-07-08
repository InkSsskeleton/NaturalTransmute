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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class HCCreateInfestedBlockRecipe extends HarmoniousChangeRecipe {

    public HCCreateInfestedBlockRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    protected boolean extraMatches(HarmoniousChangeRecipeInput input) {
        Block block = Block.byItem(input.getItem(0).getItem());
        return block != Blocks.AIR && InfestedBlock.isCompatibleHostBlock(block.defaultBlockState());
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        BlockState state = Block.byItem(input.getItem(0).getItem()).defaultBlockState();
        return new ItemStack(InfestedBlock.infestedStateByHost(state).getBlock());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            BlockState state = Block.byItem(item).defaultBlockState();
            if (InfestedBlock.isCompatibleHostBlock(state)) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()), Ingredient.of(Items.SPIDER_EYE));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_SNOWY_SLOPES.get(),
                NTItems.H_MEADOW.get(), NTItems.H_CHERRY_GROVE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_CREATE_INFESTED_BLOCK_SERIALIZER.get();
    }

}