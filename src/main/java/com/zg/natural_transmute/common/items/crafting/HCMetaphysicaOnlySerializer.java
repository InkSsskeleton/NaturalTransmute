package com.zg.natural_transmute.common.items.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HCMetaphysicaOnlySerializer<T extends HarmoniousChangeRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;

    public HCMetaphysicaOnlySerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("metaphysicas")
                        .forGetter(HarmoniousChangeRecipe::getMetaphysicas)
        ).apply(instance, factory::create));
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer) {
        return this.factory.create(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getMetaphysicas());
    }

    public interface Factory<T extends HarmoniousChangeRecipe> {

        T create(Ingredient metaphysicas);

    }

}