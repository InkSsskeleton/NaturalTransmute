package com.zg.natural_transmute.common.data;

import com.google.common.collect.Maps;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class NTBlockFamilies {

    public static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily END_ALSOPHILA_PLANKS =
            familyBuilder(NTBlocks.END_ALSOPHILA_PLANKS.get())
                    .pressurePlate(NTBlocks.END_ALSOPHILA_FAMILY.getFirst().get())
                    .fenceGate(NTBlocks.END_ALSOPHILA_FAMILY.get(1).get())
                    .trapdoor(NTBlocks.END_ALSOPHILA_FAMILY.get(2).get())
                    .button(NTBlocks.END_ALSOPHILA_FAMILY.get(3).get())
                    .door(NTBlocks.END_ALSOPHILA_FAMILY.get(4).get())
                    .stairs(NTBlocks.END_ALSOPHILA_FAMILY.get(5).get())
                    .fence(NTBlocks.END_ALSOPHILA_FAMILY.get(6).get())
                    .slab(NTBlocks.END_ALSOPHILA_FAMILY.getLast().get()).getFamily();

    public static final BlockFamily BLUE_NETHER_BRICKS =
            familyBuilder(NTBlocks.BLUE_NETHER_BRICKS.get())
                    .slab(NTBlocks.BLUE_NETHER_BRICK_SLAB.get())
                    .stairs(NTBlocks.BLUE_NETHER_BRICK_STAIRS.get())
                    .wall(NTBlocks.BLUE_NETHER_BRICK_WALL.get())
                    .getFamily();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockFamily = MAP.put(baseBlock, builder.getFamily());
        BlockFamilies.MAP.put(baseBlock, builder.getFamily());
        if (blockFamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }

}