package com.zg.natural_transmute.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zg.natural_transmute.common.items.crafting.special.HCBlockFamilyTransferRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

public class HCBlockFamilyTransferSerializer implements RecipeSerializer<HCBlockFamilyTransferRecipe> {

    private static final Codec<Block> BLOCK_CODEC = BuiltInRegistries.BLOCK.byNameCodec();
    private static final StreamCodec<RegistryFriendlyByteBuf, Block> BLOCK_STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(BLOCK_CODEC);

    private static final MapCodec<HCBlockFamilyTransferRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BLOCK_CODEC.fieldOf("old").forGetter(HCBlockFamilyTransferRecipe::getOldBaseBlock),
                    BLOCK_CODEC.fieldOf("new").forGetter(HCBlockFamilyTransferRecipe::getNewBaseBlock),
                    Ingredient.CODEC.listOf(0, 2).fieldOf("ingredients").flatXmap(list -> {
                        Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                        }, DataResult::success).forGetter(HCBlockFamilyTransferRecipe::getExtraIngredients),
                    Ingredient.CODEC_NONEMPTY.fieldOf("biome_catalysts").forGetter(HCBlockFamilyTransferRecipe::getMetaphysicas)
            ).apply(instance, HCBlockFamilyTransferRecipe::new));

    @Override
    public MapCodec<HCBlockFamilyTransferRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, HCBlockFamilyTransferRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private HCBlockFamilyTransferRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        Block oldBlock = BLOCK_STREAM_CODEC.decode(buffer);
        Block newBlock = BLOCK_STREAM_CODEC.decode(buffer);
        int size = buffer.readVarInt();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

        for (int i = 0; i < size; i++) {
            try {
                Ingredient decoded = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                ingredients.set(i, decoded.isEmpty() ? Ingredient.EMPTY : decoded);
            } catch (Exception e) {
                ingredients.set(i, Ingredient.EMPTY);
            }
        }

        Ingredient biome_catalysts = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        return new HCBlockFamilyTransferRecipe(oldBlock, newBlock, ingredients, biome_catalysts);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, HCBlockFamilyTransferRecipe recipe) {
        BLOCK_STREAM_CODEC.encode(buffer, recipe.getOldBaseBlock());
        BLOCK_STREAM_CODEC.encode(buffer, recipe.getNewBaseBlock());
        buffer.writeVarInt(recipe.getExtraIngredients().size()); // 添加这一行
        for (Ingredient ingredient : recipe.getExtraIngredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getMetaphysicas());
    }

}