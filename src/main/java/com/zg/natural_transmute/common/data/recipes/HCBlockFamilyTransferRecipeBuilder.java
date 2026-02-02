package com.zg.natural_transmute.common.data.recipes;

import com.zg.natural_transmute.common.items.crafting.special.HCBlockFamilyTransferRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class HCBlockFamilyTransferRecipeBuilder implements RecipeBuilder {

    private final Block oldBaseBlock;
    private final Block newBaseBlock;
    private final Ingredient biome_catalysts;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private HCBlockFamilyTransferRecipeBuilder(Block oldBaseBlock, Block newBaseBlock, Ingredient biome_catalysts) {
        this.oldBaseBlock = oldBaseBlock;
        this.newBaseBlock = newBaseBlock;
        this.biome_catalysts = biome_catalysts;
    }

    public static HCBlockFamilyTransferRecipeBuilder addRecipe(Block oldBaseBlock, Block newBaseBlock, Ingredient biome_catalysts) {
        return new HCBlockFamilyTransferRecipeBuilder(oldBaseBlock, newBaseBlock, biome_catalysts);
    }

    public HCBlockFamilyTransferRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public HCBlockFamilyTransferRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public HCBlockFamilyTransferRecipeBuilder requires(ItemLike item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public HCBlockFamilyTransferRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public HCBlockFamilyTransferRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public HCBlockFamilyTransferRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (this.criteria.isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HCBlockFamilyTransferRecipe recipe = new HCBlockFamilyTransferRecipe(this.oldBaseBlock, this.newBaseBlock, this.ingredients, this.biome_catalysts);
        recipeOutput.accept(id.withPrefix("harmonious_change/"), recipe, builder.build(id.withPrefix("recipes/harmonious_change/")));
    }

}