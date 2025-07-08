package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import java.util.List;

public class HCSuspiciousStewRecipe extends HarmoniousChangeRecipe {

    public HCSuspiciousStewRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        SuspiciousEffectHolder holder = SuspiciousEffectHolder.tryGet(input.getItem(0).getItem());
        ItemStack resultStack = new ItemStack(Items.SUSPICIOUS_STEW);
        if (holder != null) {
            resultStack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, holder.getSuspiciousEffects());
        }

        return resultStack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            if (SuspiciousEffectHolder.tryGet(item) != null) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()), Ingredient.of(Items.BOWL));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_MUSHROOM.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_SUSPICIOUS_STEW_SERIALIZER.get();
    }

}