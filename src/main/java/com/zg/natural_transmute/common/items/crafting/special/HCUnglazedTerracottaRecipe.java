package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class HCUnglazedTerracottaRecipe extends HarmoniousChangeRecipe {

    public HCUnglazedTerracottaRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    protected boolean extraMatches(HarmoniousChangeRecipeInput input) {
        return Block.byItem(input.getItem(0).getItem()) instanceof GlazedTerracottaBlock;
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        String path = BuiltInRegistries.ITEM.getKey(input.getItem(0).getItem()).getPath();
        path = path.replaceFirst("_glazed", StringUtils.EMPTY);
        ResourceLocation key = ResourceLocation.withDefaultNamespace(path);
        return new ItemStack(BuiltInRegistries.ITEM.get(key));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            if (Block.byItem(item) instanceof GlazedTerracottaBlock) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_UNGLAZED_TERRACOTTA_SERIALIZER.get();
    }

}