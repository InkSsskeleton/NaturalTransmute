package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.common.blocks.entity.SimpleContainerBlockEntity;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NTCommonUtils {

    public static Stream<Block> getKnownBlockStream() {
        return NTBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get);
    }

    public static Stream<Item> getKnownItemStream() {
        return NTItems.ITEMS.getEntries().stream().map(DeferredHolder::get);
    }

    public static Iterable<Block> getKnownBlocks() {
        return NTBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }

    public static Iterable<Item> getKnownItems() {
        return NTItems.ITEMS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }

    public static void consumeIngredients(SimpleContainerBlockEntity table) {
        for (int i = 0; i < 3; i++) {
            ItemStack item = table.getItem(i);
            if (item.hasCraftingRemainingItem()) {
                ItemStack remainingItem = item.getCraftingRemainingItem();
                if (item.getCount() == 1) {
                    table.setItem(i, remainingItem);
                } else {
                    Level level = table.getLevel();
                    if (level != null) {
                        float x = table.getBlockPos().getX() + 0.5F;
                        float y = table.getBlockPos().getY() + 1.2F;
                        float z = table.getBlockPos().getZ() + 0.5F;
                        Containers.dropItemStack(level, x, y, z, remainingItem);
                        item.shrink(1);
                    }
                }
            } else {
                item.shrink(1);
            }
        }
    }

}