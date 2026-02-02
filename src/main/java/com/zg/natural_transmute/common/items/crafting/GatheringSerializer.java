package com.zg.natural_transmute.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class GatheringSerializer<T extends GatheringRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public GatheringSerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input1").forGetter(recipe -> recipe.input1),
                Ingredient.CODEC_NONEMPTY.fieldOf("input2").forGetter(recipe -> recipe.input2),
                ItemStack.CODEC.fieldOf("core").forGetter(recipe -> recipe.core),
                ItemStack.CODEC.fieldOf("results").forGetter(recipe -> recipe.result),
                Codec.INT.fieldOf("gathering_time").forGetter(recipe -> recipe.gatheringTime)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack core = ItemStack.STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        int gatheringTime = buffer.readVarInt();

        if (gatheringTime < 0) gatheringTime = 0;

        return this.factory.create(input1, input2, core, result, gatheringTime);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input1);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input2);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.core);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeVarInt(recipe.gatheringTime);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface Factory<T extends GatheringRecipe> {
        T create(Ingredient input1, Ingredient input2, ItemStack core, ItemStack result, int gatheringTime);
    }

}