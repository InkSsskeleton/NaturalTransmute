package com.zg.natural_transmute.common.items.crafting.special;

import com.google.common.collect.Lists;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/** @noinspection deprecation*/
public class HCPlantToWheatRecipe extends HarmoniousChangeRecipe {

    public HCPlantToWheatRecipe() {
        super(NonNullList.create(), NonNullList.create(), Ingredient.EMPTY);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(Items.WHEAT);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        List<ItemStack> items = Lists.newArrayList();
        ClientLevel level = Minecraft.getInstance().level;
        BlockState state = Blocks.AIR.defaultBlockState();
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof BushBlock && block.asItem() != Items.AIR && level != null) {
                ItemStack itemStack = block.getCloneItemStack(level, BlockPos.ZERO, state);
                if (!itemStack.isEmpty()) items.add(itemStack);
            }
        });

        return NonNullList.of(Ingredient.of(items.stream()));
    }

    @Override
    public Ingredient getMetaphysicas() {
        return Ingredient.of(NTItems.H_SAVANNA.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_PLANT_TO_WHEAT_SERIALIZER.get();
    }

}