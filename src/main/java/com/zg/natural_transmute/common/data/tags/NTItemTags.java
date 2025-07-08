package com.zg.natural_transmute.common.data.tags;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NTItemTags {

    public static final TagKey<Item> FRUIT = create("fruit");
    public static final TagKey<Item> GRASS = create("grass");
    public static final TagKey<Item> METAPHYSICA = create("metaphysica");
    public static final TagKey<Item> VEGETABLE = create("vegetable");
    public static final TagKey<Item> END_ALSOPHILA_LOGS = create("end_alsophila_logs");
    public static final TagKey<Item> END_ALSOPHILA_SAPLING_PLACEABLE = create("end_alsophila_sapling_placeable");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(NaturalTransmute.prefix(name));
    }

}