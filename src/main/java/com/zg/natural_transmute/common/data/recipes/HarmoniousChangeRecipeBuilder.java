package com.zg.natural_transmute.common.data.recipes;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class HarmoniousChangeRecipeBuilder implements RecipeBuilder {

    public final NonNullList<Ingredient> ingredients = NonNullList.create();
    public final NonNullList<ItemStack> excepts = NonNullList.create();
    public final NonNullList<ItemStack> results = NonNullList.create();
    public final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    public final Ingredient biome_catalyst;
    public int time = 160;
    public boolean consume = true;
    public String name = StringUtils.EMPTY;

    private HarmoniousChangeRecipeBuilder(ItemLike... biome_catalyst) {
        this.biome_catalyst = Ingredient.of(biome_catalyst);
    }

    public static HarmoniousChangeRecipeBuilder addRecipe(ItemLike... biome_catalyst) {
        return new HarmoniousChangeRecipeBuilder(biome_catalyst);
    }

    public HarmoniousChangeRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public HarmoniousChangeRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public HarmoniousChangeRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public HarmoniousChangeRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public HarmoniousChangeRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public HarmoniousChangeRecipeBuilder excepts(ItemLike itemLike) {
        ItemStack itemStack = new ItemStack(itemLike);
        if (!itemStack.isEmpty()) {
            this.excepts.add(itemStack);
        }

        return this;
    }

    public HarmoniousChangeRecipeBuilder results(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            this.results.add(itemStack);
        }

        return this;
    }

    public HarmoniousChangeRecipeBuilder results(ItemLike itemLike) {
        return this.results(itemLike, 1);
    }

    public HarmoniousChangeRecipeBuilder results(ItemLike itemLike, int count) {
        ItemStack itemStack = new ItemStack(itemLike);
        if (!itemStack.isEmpty()) {
            itemStack.setCount(count);
            this.results.add(itemStack);
        }

        return this;
    }

    public HarmoniousChangeRecipeBuilder time(int time) {
        this.time = time;
        return this;
    }

    public HarmoniousChangeRecipeBuilder consume(boolean consume) {
        this.consume = consume;
        return this;
    }

    public HarmoniousChangeRecipeBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.results.getFirst().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (this.criteria.isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HarmoniousChangeRecipe recipe = new HarmoniousChangeRecipe(this.ingredients, this.excepts, this.results, this.biome_catalyst, this.time, this.consume);
        recipeOutput.accept(id.withPrefix("harmonious_change/"), recipe, builder.build(id.withPrefix("recipes/harmonious_change/")));
    }

}