package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.common.data.tags.NTItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.StreamSupport;

public class HarmoniousChangeFuelUtils {
    public static List<Item> getFuelsItemView() {
        return StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(NTItemTags.HARMONIOUS_CHANGE_FUEL).spliterator(), false)
                .map(Holder::value)
                .toList();
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.is(NTItemTags.HARMONIOUS_CHANGE_FUEL);
    }

    public static boolean isCoalFuel(ItemStack stack) {
        return stack.is(NTItemTags.HARMONIOUS_CHANGE_FUEL_COAL);
    }

    public static boolean isBucketFuel(ItemStack stack) {
        return stack.is(NTItemTags.HARMONIOUS_CHANGE_FUEL_BUCKET);
    }

    public static boolean isEternalFuel(ItemStack stack) {
        return stack.is(NTItemTags.HARMONIOUS_CHANGE_ETERNAL_FUEL);
    }
}
