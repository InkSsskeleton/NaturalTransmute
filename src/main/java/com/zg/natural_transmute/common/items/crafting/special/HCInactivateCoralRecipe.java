package com.zg.natural_transmute.common.items.crafting.special;

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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HCInactivateCoralRecipe extends HarmoniousChangeRecipe {

    private static final Map<ItemStack, ItemStack> RECIPES = new HashMap<>();

    public HCInactivateCoralRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        return RECIPES.get(input.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.of(RECIPES.keySet().stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_INACTIVATE_CORAL_SERIALIZER.get();
    }

    static {
        BuiltInRegistries.BLOCK.forEach(block -> {
            try {
                Field field = block.getClass().getDeclaredField("deadBlock");
                if (field.get(block) instanceof Block deadBlock && block.asItem() != Items.AIR) {
                    RECIPES.put(new ItemStack(block), new ItemStack(deadBlock));
                }
            } catch (Exception ignored) {}
        });
    }

}