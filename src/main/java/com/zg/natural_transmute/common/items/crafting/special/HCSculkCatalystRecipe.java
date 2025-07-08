package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HCSculkCatalystRecipe extends HCInactivateCoralRecipe {

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        return new ItemStack(Items.SCULK_CATALYST);
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_DEEP_DARK.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_SCULK_CATALYST_SERIALIZER.get();
    }

}