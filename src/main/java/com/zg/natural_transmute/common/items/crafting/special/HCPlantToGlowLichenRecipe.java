package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HCPlantToGlowLichenRecipe extends HCPlantToWheatRecipe {

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(Items.GLOW_LICHEN);
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_DEEPSLATE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_PLANT_TO_GLOW_LICHEN_SERIALIZER.get();
    }

}