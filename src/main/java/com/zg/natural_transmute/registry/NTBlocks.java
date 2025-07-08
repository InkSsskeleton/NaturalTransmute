package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.*;
import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import com.zg.natural_transmute.common.blocks.state.NTBlockSetType;
import com.zg.natural_transmute.common.blocks.state.NTWoodType;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import static com.zg.natural_transmute.common.blocks.state.NTBlockProperties.ofFullCopy;
import static com.zg.natural_transmute.utils.NTBlockRegUtils.*;

public class NTBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Block, LiquidBlock> MINE_WATER = BLOCKS.register("mine_water",
            () -> new LiquidBlock(NTFluids.MINE_WATER_STILL.get(), ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<Block, Block> TURQUOISE = normal("turquoise",
            ofFullCopy(Blocks.DEEPSLATE).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> CORUNDUM = register("corundum",
            () -> new RotatedPillarBlock(ofFullCopy(Blocks.DEEPSLATE).useSimpleBlockItem()));
    public static final DeferredHolder<Block, Block> AMBER_BLOCK = register("amber_block", AmberBlock::new);
    public static final DeferredHolder<Block, Block> AZURE_FROGLIGHT = register("azure_froglight",
            () -> new RotatedPillarBlock(ofFullCopy(Blocks.OCHRE_FROGLIGHT).useSimpleBlockItem()));
    public static final DeferredHolder<Block, Block> BUTTERCUP = register("buttercup",
            () -> new FlowerBlock(SuspiciousStewEffects.EMPTY, ofFullCopy(Blocks.POPPY)));
    public static final DeferredHolder<Block, Block> CAVE_EARTH = normal("cave_earth", ofFullCopy(Blocks.DIRT).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> DEATH_EARTH = normal("death_earth", ofFullCopy(Blocks.DIRT).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> GRASSLAND_EARTH = normal("grassland_earth", ofFullCopy(Blocks.DIRT).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> OCEAN_EARTH = normal("ocean_earth", ofFullCopy(Blocks.DIRT).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> HETEROGENEOUS_STONE_ORE =
            ore("heterogeneous_stone_ore", UniformInt.of((2), (5)),
                    ofFullCopy(Blocks.LAPIS_ORE).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> DEEPSLATE_HETEROGENEOUS_STONE_ORE =
            ore("deepslate_heterogeneous_stone_ore", UniformInt.of((2), (5)),
                    ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE).isSimpleModelBlock().useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> REED = BLOCKS.register("reed", Reed::new);
    public static final DeferredHolder<Block, Block> PITAYA = BLOCKS.register("pitaya", Pitaya::new);
    public static final DeferredHolder<Block, Block> PAPYRUS = BLOCKS.register("papyrus", Papyrus::new);
    public static final DeferredHolder<Block, Block> PINEAPPLE = BLOCKS.register("pineapple", Pineapple::new);
    public static final DeferredHolder<Block, Block> PEAT_MOSS = BLOCKS.register("peat_moss", PeatMoss::new);
    public static final DeferredHolder<Block, Block> PLANTAIN = BLOCKS.register("plantain", Plantain::new);
    public static final DeferredHolder<Block, Block> WARPED_WART = BLOCKS.register("warped_wart", WarpedWart::new);
    public static final DeferredHolder<Block, Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", BlueberryBush::new);
    public static final DeferredHolder<Block, Block> BLUE_TARO_VINE = register("blue_taro_vine", BlueTaroVine::new);
    public static final DeferredHolder<Block, Block> ALGAL_END_STONE = register("algal_end_stone", AlgalEndStone::new);
    public static final DeferredHolder<Block, Block> SIMULATED_RAMBLER = register("simulated_rambler", SimulatedRambler::new);
    public static final DeferredHolder<Block, Block> PLANTAIN_SAPLING = register("plantain_sapling",
            () -> new SaplingBlock(NTTreeGrowers.PLANTAIN, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredHolder<Block, Block> PLANTAIN_LEAVES = register("plantain_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final DeferredHolder<Block, Block> PLANTAIN_STEM = wood("plantain_stem", MapColor.COLOR_LIGHT_GREEN, 1.0F);
    public static final DeferredHolder<Block, Block> BLUE_NETHER_BRICKS = normal("blue_nether_bricks", ofFullCopy(Blocks.NETHER_BRICKS).useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> BLUE_NETHER_BRICK_SLAB = slab("blue_nether_brick_slab", BLUE_NETHER_BRICKS, ofFullCopy(Blocks.NETHER_BRICKS));
    public static final DeferredHolder<Block, Block> BLUE_NETHER_BRICK_STAIRS = stair("blue_nether_brick_stairs", BLUE_NETHER_BRICKS, ofFullCopy(Blocks.NETHER_BRICKS));
    public static final DeferredHolder<Block, Block> BLUE_NETHER_BRICK_WALL = wall("blue_nether_brick_wall", BLUE_NETHER_BRICKS, ofFullCopy(Blocks.NETHER_BRICKS));
    public static final DeferredHolder<Block, Block> STRIPPED_END_ALSOPHILA_LOG = wood("stripped_end_alsophila_log", MapColor.COLOR_BLUE, 2.0F);
    public static final DeferredHolder<Block, Block> STRIPPED_END_ALSOPHILA_WOOD = wood("stripped_end_alsophila_wood", MapColor.COLOR_BLUE, 2.0F);
    public static final DeferredHolder<Block, Block> END_ALSOPHILA_LEAVES = register("end_alsophila_leaves", () -> Blocks.leaves(SoundType.GRASS));
    public static final DeferredHolder<Block, Block> END_ALSOPHILA_PLANKS = normal("end_alsophila_planks", ofFullCopy(Blocks.OAK_PLANKS).useSimpleBlockItem());
    public static final DeferredHolder<Block, Block> END_ALSOPHILA_LOG = wood("end_alsophila_log", STRIPPED_END_ALSOPHILA_LOG, MapColor.COLOR_BLUE, 2.0F);
    public static final DeferredHolder<Block, Block> END_ALSOPHILA_WOOD = wood("end_alsophila_wood", STRIPPED_END_ALSOPHILA_WOOD, MapColor.COLOR_BLUE, 2.0F);
    public static final DeferredHolder<Block, Block> END_ALSOPHILA_SAPLING = register("end_alsophila_sapling", EndAlsophilaSapling::new);
    public static final DeferredHolder<Block, Block> GATHERING_PLATFORM = register("gathering_platform", GatheringPlatform::new);
    public static final DeferredHolder<Block, Block> HARMONIOUS_CHANGE_STOVE = register("harmonious_change_stove", HarmoniousChangeStove::new);

    public static final DeferredHolder<Block, Block> ACTIVATED_TUBE_CORAL_BLOCK = BLOCKS.register("activated_tube_coral_block",
            () -> new ActivatedCoralBlock(Blocks.DEAD_TUBE_CORAL_BLOCK, MapColor.COLOR_BLUE, Items.TUBE_CORAL_BLOCK));
    public static final DeferredHolder<Block, Block> ACTIVATED_BRAIN_CORAL_BLOCK = BLOCKS.register("activated_brain_coral_block",
            () -> new ActivatedCoralBlock(Blocks.DEAD_BRAIN_CORAL_BLOCK, MapColor.COLOR_PINK, Items.BRAIN_CORAL_BLOCK));
    public static final DeferredHolder<Block, Block> ACTIVATED_BUBBLE_CORAL_BLOCK = BLOCKS.register("activated_bubble_coral_block",
            () -> new ActivatedCoralBlock(Blocks.DEAD_BUBBLE_CORAL_BLOCK, MapColor.COLOR_PURPLE, Items.BUBBLE_CORAL_BLOCK));
    public static final DeferredHolder<Block, Block> ACTIVATED_FIRE_CORAL_BLOCK = BLOCKS.register("activated_fire_coral_block",
            () -> new ActivatedCoralBlock(Blocks.DEAD_FIRE_CORAL_BLOCK, MapColor.COLOR_RED, Items.FIRE_CORAL_BLOCK));
    public static final DeferredHolder<Block, Block> ACTIVATED_HORN_CORAL_BLOCK = BLOCKS.register("activated_horn_coral_block",
            () -> new ActivatedCoralBlock(Blocks.DEAD_HORN_CORAL_BLOCK, MapColor.COLOR_YELLOW, Items.HORN_CORAL_BLOCK));
    public static final DeferredHolder<Block, Block> ACTIVATED_TUBE_CORAL_FAN = BLOCKS.register("activated_tube_coral_fan",
            () -> new ActivatedCoralFan(Blocks.DEAD_TUBE_CORAL_FAN, MapColor.COLOR_BLUE, Items.TUBE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_BRAIN_CORAL_FAN = BLOCKS.register("activated_brain_coral_fan",
            () -> new ActivatedCoralFan(Blocks.DEAD_BRAIN_CORAL_FAN, MapColor.COLOR_PINK, Items.BRAIN_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_BUBBLE_CORAL_FAN = BLOCKS.register("activated_bubble_coral_fan",
            () -> new ActivatedCoralFan(Blocks.DEAD_BUBBLE_CORAL_FAN, MapColor.COLOR_PURPLE, Items.BUBBLE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_FIRE_CORAL_FAN = BLOCKS.register("activated_fire_coral_fan",
            () -> new ActivatedCoralFan(Blocks.DEAD_FIRE_CORAL_FAN, MapColor.COLOR_RED, Items.FIRE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_HORN_CORAL_FAN = BLOCKS.register("activated_horn_coral_fan",
            () -> new ActivatedCoralFan(Blocks.DEAD_HORN_CORAL_FAN, MapColor.COLOR_YELLOW, Items.HORN_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_TUBE_CORAL = BLOCKS.register("activated_tube_coral",
            () -> new ActivatedCoralPlant(Blocks.DEAD_TUBE_CORAL, MapColor.COLOR_BLUE, Items.TUBE_CORAL));
    public static final DeferredHolder<Block, Block> ACTIVATED_BRAIN_CORAL = BLOCKS.register("activated_brain_coral",
            () -> new ActivatedCoralPlant(Blocks.DEAD_BRAIN_CORAL, MapColor.COLOR_PINK, Items.BRAIN_CORAL));
    public static final DeferredHolder<Block, Block> ACTIVATED_BUBBLE_CORAL = BLOCKS.register("activated_bubble_coral",
            () -> new ActivatedCoralPlant(Blocks.DEAD_BUBBLE_CORAL, MapColor.COLOR_PURPLE, Items.BUBBLE_CORAL));
    public static final DeferredHolder<Block, Block> ACTIVATED_FIRE_CORAL = BLOCKS.register("activated_fire_coral",
            () -> new ActivatedCoralPlant(Blocks.DEAD_FIRE_CORAL, MapColor.COLOR_RED, Items.FIRE_CORAL));
    public static final DeferredHolder<Block, Block> ACTIVATED_HORN_CORAL = BLOCKS.register("activated_horn_coral",
            () -> new ActivatedCoralPlant(Blocks.DEAD_HORN_CORAL, MapColor.COLOR_YELLOW, Items.HORN_CORAL));
    public static final DeferredHolder<Block, Block> ACTIVATED_TUBE_CORAL_WALL_FAN = BLOCKS.register("activated_tube_coral_wall_fan",
            () -> new ActivatedCoralWallFan(Blocks.DEAD_TUBE_CORAL_WALL_FAN, MapColor.COLOR_BLUE, Items.TUBE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_BRAIN_CORAL_WALL_FAN = BLOCKS.register("activated_brain_coral_wall_fan",
            () -> new ActivatedCoralWallFan(Blocks.DEAD_BRAIN_CORAL_WALL_FAN, MapColor.COLOR_PINK, Items.BRAIN_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_BUBBLE_CORAL_WALL_FAN = BLOCKS.register("activated_bubble_coral_wall_fan",
            () -> new ActivatedCoralWallFan(Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, MapColor.COLOR_PURPLE, Items.BUBBLE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_FIRE_CORAL_WALL_FAN = BLOCKS.register("activated_fire_coral_wall_fan",
            () -> new ActivatedCoralWallFan(Blocks.DEAD_FIRE_CORAL_WALL_FAN, MapColor.COLOR_RED, Items.FIRE_CORAL_FAN));
    public static final DeferredHolder<Block, Block> ACTIVATED_HORN_CORAL_WALL_FAN = BLOCKS.register("activated_horn_coral_wall_fan",
            () -> new ActivatedCoralWallFan(Blocks.DEAD_HORN_CORAL_WALL_FAN, MapColor.COLOR_YELLOW, Items.HORN_CORAL_FAN));

    public static final DeferredHolder<Block, Block> POTTED_BUTTERCUP = flowerPot(BUTTERCUP);
    public static final DeferredHolder<Block, Block> POTTED_PLANTAIN_SAPLING = flowerPot(PLANTAIN_SAPLING);
    public static final DeferredHolder<Block, Block> POTTED_END_ALSOPHILA_SAPLING = flowerPot(END_ALSOPHILA_SAPLING);

    public static final List<DeferredHolder<Block, Block>> END_ALSOPHILA_FAMILY = registerWoodFamily("end_alsophila",
            END_ALSOPHILA_PLANKS, ofFullCopy(Blocks.OAK_PLANKS), NTBlockSetType.END_ALSOPHILA, NTWoodType.END_ALSOPHILA);

    public static<T extends Block> List<DeferredHolder<Block, Block>> registerWoodFamily(
            String name, Supplier<T> base, NTBlockProperties properties, BlockSetType blockSetType, WoodType woodType) {
        DeferredHolder<Block, Block> pressuredPlate = pressurePlate(name + "_pressure_plate", base, properties, blockSetType);
        DeferredHolder<Block, Block> fencedGate = fenceGate(name + "_fence_gate", base, properties, woodType);
        DeferredHolder<Block, Block> trapdoor = trapdoor(name + "_trapdoor", base, properties, blockSetType);
        DeferredHolder<Block, Block> button = button(name + "_button", base, Boolean.TRUE, blockSetType);
        DeferredHolder<Block, Block> door = door(name + "_door", base, properties, blockSetType);
        DeferredHolder<Block, Block> stair = stair(name + "_stairs", base, properties);
        DeferredHolder<Block, Block> fence = fence(name + "_fence", base, properties);
        DeferredHolder<Block, Block> slab = slab(name + "_slab", base, properties);
        return List.of(pressuredPlate, fencedGate, trapdoor, button, door, stair, fence, slab);
    }

}