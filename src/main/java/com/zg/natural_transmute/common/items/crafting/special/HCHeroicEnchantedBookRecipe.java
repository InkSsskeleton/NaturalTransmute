package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTEnchantments;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;

public class HCHeroicEnchantedBookRecipe extends HarmoniousChangeRecipe {

    public HCHeroicEnchantedBookRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        HolderLookup.RegistryLookup<Enchantment> lookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.enchant(lookup.getOrThrow(NTEnchantments.HEROIC), 1);
        return enchantedBook;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.of(Items.BLUE_ICE),
                Ingredient.of(NTItems.HONEY_WINE.get()),
                Ingredient.of(Items.ENCHANTED_BOOK));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_FROZEN_OCEAN.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_HEROIC_ENCHANTED_BOOK_SERIALIZER.get();
    }

}