package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HCRefrigeratedRocketRecipe extends HarmoniousChangeRecipe {

    public HCRefrigeratedRocketRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        Fireworks fireworks = input.getItem(0).get(DataComponents.FIREWORKS);
        ItemStack resultStack = new ItemStack(NTItems.REFRIGERATED_ROCKET);
        if (fireworks != null) {
            resultStack.set(DataComponents.FIREWORKS, fireworks);
        }

        return resultStack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.of(Items.FIREWORK_ROCKET));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_SNOWY_SLOPES.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_REFRIGERATED_ROCKET_SERIALIZER.get();
    }

}