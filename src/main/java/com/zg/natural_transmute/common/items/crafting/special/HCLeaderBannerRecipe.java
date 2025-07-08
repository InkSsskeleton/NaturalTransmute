package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class HCLeaderBannerRecipe extends HarmoniousChangeRecipe {

    public HCLeaderBannerRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return Raid.getLeaderBannerInstance(registries.lookupOrThrow(Registries.BANNER_PATTERN));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        BuiltInRegistries.ITEM.forEach(item -> {
            if (item.components().has(DataComponents.BANNER_PATTERNS)) {
                items.add(new ItemStack(item));
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_DARK_FOREST.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_LEADER_BANNER_SERIALIZER.get();
    }

}