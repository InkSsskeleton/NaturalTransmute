package com.zg.natural_transmute.common.items.crafting.special;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.data.NTBlockFamilies;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HCBlockFamilyTransferRecipe extends HarmoniousChangeRecipe {

    private final BlockFamily oldFamily;
    private final BlockFamily newFamily;
    private final Ingredient metaphysicas;

    public HCBlockFamilyTransferRecipe(Block oldBaseBlock, Block newBaseBlock, NonNullList<Ingredient> ingredients, Ingredient metaphysicas) {
        super(ingredients, NonNullList.create(), Ingredient.EMPTY);
        this.oldFamily = BlockFamilies.MAP.containsKey(oldBaseBlock) ? BlockFamilies.MAP.get(oldBaseBlock) : NTBlockFamilies.MAP.get(oldBaseBlock);
        this.newFamily = BlockFamilies.MAP.containsKey(newBaseBlock) ? BlockFamilies.MAP.get(newBaseBlock) : NTBlockFamilies.MAP.get(newBaseBlock);
        this.metaphysicas = metaphysicas;
    }

    @Override
    public boolean matches(HarmoniousChangeRecipeInput input, Level level) {
        if (input.ingredientCount() != this.getIngredients().size()) {
            return false;
        } else {
            boolean hasFuel = HarmoniousChangeStoveBlockEntity.getFuel().containsKey(input.getItem(3).getItem());
            return hasFuel && this.metaphysicas.test(input.getItem(4)) && this.extraMatches(input);
        }
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        return this.getRecipes().get(input.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        Stream<ItemStack> stream = this.getRecipes().keySet().stream();
        List<Ingredient> list = new ArrayList<>(List.of(Ingredient.of(stream)));
        if (!this.ingredients.isEmpty()) {
            list.addAll(this.ingredients);
        }

        return NonNullList.copyOf(list);
    }

    public Block getOldBaseBlock() {
        return this.oldFamily.getBaseBlock();
    }

    public Block getNewBaseBlock() {
        return this.newFamily.getBaseBlock();
    }

    public NonNullList<Ingredient> getExtraIngredients() {
        return this.ingredients;
    }

    public Ingredient getMetaphysicas() {
        return this.metaphysicas;
    }

    private Map<ItemStack, ItemStack> getRecipes() {
        Map<BlockFamily.Variant, Block> oldVariants = this.oldFamily.getVariants();
        Map<BlockFamily.Variant, Block> newVariants = this.newFamily.getVariants();
        ItemStack oldStack = new ItemStack(this.oldFamily.getBaseBlock());
        ItemStack newStack = new ItemStack(this.newFamily.getBaseBlock());
        Map<ItemStack, ItemStack> stackMap = oldVariants.entrySet().stream()
                .filter(entry -> newVariants.containsKey(entry.getKey()))
                .collect(Collectors.toMap(entry -> new ItemStack(entry.getValue()),
                        entry -> new ItemStack(newVariants.get(entry.getKey()))));
        stackMap.put(oldStack, newStack);
        return stackMap;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipeSerializers.HC_BLOCK_FAMILY_TRANSFER_SERIALIZER.get();
    }

}