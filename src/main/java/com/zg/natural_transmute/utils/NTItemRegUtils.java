package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.common.components.AssociatedBiomes;
import com.zg.natural_transmute.common.items.StrangeCatFood;
import com.zg.natural_transmute.common.items.StrangeDogFood;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class NTItemRegUtils {

    public static DeferredHolder<Item, Item> normal(String name) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    }

    public static DeferredHolder<Item, Item> alias(String name, DeferredHolder<Block, Block> block, Item.Properties properties) {
        return NTItems.ITEMS.register(name, () -> new ItemNameBlockItem(block.get(), properties));
    }

    public static DeferredHolder<Item, Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int bgColor, int hlColor) {
        return NTItems.ITEMS.register(name + "_spawn_egg", () -> new DeferredSpawnEggItem(type, bgColor, hlColor, new Item.Properties()));
    }

    public static DeferredHolder<Item, Item> catFood(String name, @Nullable ResourceKey<CatVariant> variant) {
        return NTItems.ITEMS.register(name, () -> new StrangeCatFood(variant));
    }

    public static DeferredHolder<Item, Item> dogFood(String name, ResourceKey<WolfVariant> variant) {
        return NTItems.ITEMS.register(name, () -> new StrangeDogFood(variant));
    }

    public static DeferredHolder<Item, Item> fx(String name, List<ResourceKey<Biome>> biomes) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties()
                .component(NTDataComponents.ASSOCIATED_BIOMES.get(), new AssociatedBiomes(biomes))
                .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    }

    public static DeferredHolder<Item, Item> food(String name, int nutrition, float saturation) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties().food(
                new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
    }

}