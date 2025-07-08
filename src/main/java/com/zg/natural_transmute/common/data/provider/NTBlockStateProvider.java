package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.base.*;
import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.common.blocks.HarmoniousChangeStove;
import com.zg.natural_transmute.common.blocks.Papyrus;
import com.zg.natural_transmute.common.data.NTBlockFamilies;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class NTBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace(RenderType.cutout().name);
    private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace(RenderType.cutoutMipped().name);
    private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace(RenderType.translucent().name);
    private static final Map<Direction, Integer> DIRECTION_WITH_ROTATION =
            Map.of(Direction.NORTH, 0, Direction.EAST, 90,
                    Direction.SOUTH, 180, Direction.WEST, 270);

    public NTBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NaturalTransmute.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlockWithRenderType(NTBlocks.AMBER_BLOCK.get(), TRANSLUCENT);
        this.simpleBlockWithRenderType(NTBlocks.PLANTAIN_LEAVES.get(), CUTOUT_MIPPED);
        this.simpleBlockWithRenderType(NTBlocks.END_ALSOPHILA_LEAVES.get(), CUTOUT_MIPPED);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_TUBE_CORAL_BLOCK.get(), Blocks.TUBE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_BRAIN_CORAL_BLOCK.get(), Blocks.BRAIN_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_BLOCK.get(), Blocks.BUBBLE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_FIRE_CORAL_BLOCK.get(), Blocks.FIRE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_HORN_CORAL_BLOCK.get(), Blocks.HORN_CORAL_BLOCK);
        this.registerCoralFanStates(NTBlocks.ACTIVATED_TUBE_CORAL_FAN.get(), Blocks.TUBE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BRAIN_CORAL_FAN.get(), Blocks.BRAIN_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_FAN.get(), Blocks.BUBBLE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_FIRE_CORAL_FAN.get(), Blocks.FIRE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_HORN_CORAL_FAN.get(), Blocks.HORN_CORAL_FAN, "coral_fan");
        this.registerCoralStates(NTBlocks.ACTIVATED_TUBE_CORAL.get(), Blocks.TUBE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_BRAIN_CORAL.get(), Blocks.BRAIN_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_BUBBLE_CORAL.get(), Blocks.BUBBLE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_FIRE_CORAL.get(), Blocks.FIRE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_HORN_CORAL.get(), Blocks.HORN_CORAL);
        this.registerCoralFanStates(NTBlocks.ACTIVATED_TUBE_CORAL_WALL_FAN.get(), Blocks.TUBE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BRAIN_CORAL_WALL_FAN.get(), Blocks.BRAIN_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_WALL_FAN.get(), Blocks.BUBBLE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_FIRE_CORAL_WALL_FAN.get(), Blocks.FIRE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_HORN_CORAL_WALL_FAN.get(), Blocks.HORN_CORAL_FAN, "coral_wall_fan");
        this.logBlock((RotatedPillarBlock) NTBlocks.CORUNDUM.get());
        this.logBlock((RotatedPillarBlock) NTBlocks.AZURE_FROGLIGHT.get());
        this.registerCropStates(NTBlocks.BLUEBERRY_BUSH.get(), SweetBerryBushBlock.AGE);
        this.registerCropStates(NTBlocks.WARPED_WART.get(), NetherWartBlock.AGE);
        this.registerPlantStates(NTBlocks.BUTTERCUP.get());
        this.registerDoublePlantStates(NTBlocks.REED.get());
        this.registerHarmoniousChangeStoveStates(NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
        this.registerPapyrusStates(NTBlocks.PAPYRUS.get());
        this.simpleBlock(NTBlocks.MINE_WATER.get(),
                this.models().getBuilder(NTBlocks.MINE_WATER.getId().getPath())
                .texture("particle", this.mcLoc("block/water_still")));
        this.simpleBlock(NTBlocks.PEAT_MOSS.get(),
                this.models().carpet(this.name(NTBlocks.PEAT_MOSS.get()),
                        this.blockTexture(NTBlocks.PEAT_MOSS.get())));
        NTBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> this.simpleBlock(blockFamily.getBaseBlock()));
        this.logBlock((RotatedPillarBlock) NTBlocks.PLANTAIN_STEM.get());
        this.simpleBlock(NTBlocks.PLANTAIN_SAPLING.get(),
                this.models().cross(this.name(NTBlocks.PLANTAIN_SAPLING.get()),
                        this.blockTexture(NTBlocks.PLANTAIN_SAPLING.get())).renderType(CUTOUT));
        this.logBlock((RotatedPillarBlock) NTBlocks.STRIPPED_END_ALSOPHILA_LOG.get());
        this.logBlock((RotatedPillarBlock) NTBlocks.END_ALSOPHILA_LOG.get());
        this.simpleBlock(NTBlocks.END_ALSOPHILA_SAPLING.get(),
                this.models().cross(this.name(NTBlocks.END_ALSOPHILA_SAPLING.get()),
                        this.blockTexture(NTBlocks.END_ALSOPHILA_SAPLING.get())).renderType(CUTOUT));
        this.axisBlock((RotatedPillarBlock) NTBlocks.STRIPPED_END_ALSOPHILA_WOOD.get(),
                this.modLoc("block/stripped_end_alsophila_log"),
                this.modLoc("block/stripped_end_alsophila_log"));
        this.axisBlock((RotatedPillarBlock) NTBlocks.END_ALSOPHILA_WOOD.get(),
                this.modLoc("block/end_alsophila_log"),
                this.modLoc("block/end_alsophila_log"));
        for (Block block : NTCommonUtils.getKnownBlocks()) {
            if (block instanceof PressurePlateBlockWithBase pressurePlateBlock) {
                this.pressurePlateBlock(pressurePlateBlock, this.blockTexture(pressurePlateBlock.getBase()));
            } else if (block instanceof FenceGateBlockWithBase fenceGateBlock) {
                this.fenceGateBlockWithRenderType(fenceGateBlock, this.blockTexture(fenceGateBlock.getBase()), CUTOUT);
            } else if (block instanceof TrapDoorBlockWithBase trapDoorBlock) {
                ResourceLocation texture = this.blockTexture(trapDoorBlock);
                this.trapdoorBlockWithRenderType(trapDoorBlock, texture, Boolean.TRUE, CUTOUT);
                this.simpleBlockItem(trapDoorBlock, this.models().trapdoorBottom(this.name(trapDoorBlock), texture));
            } else if (block instanceof ButtonBlockWithBase buttonBlock) {
                String name = this.name(buttonBlock) + "_inventory";
                ResourceLocation texture = this.blockTexture(buttonBlock.getBase());
                ModelFile buttonInventory = this.models().buttonInventory(name, texture);
                this.buttonBlock(buttonBlock, texture);
                this.simpleBlockItem(buttonBlock, buttonInventory);
            } else if (block instanceof StairBlockWithBase stairBlock) {
                this.stairsBlock(stairBlock, this.blockTexture(stairBlock.getBase()));
            } else if (block instanceof FenceBlockWithBase fenceBlock) {
                ResourceLocation texture = this.blockTexture(fenceBlock.getBase());
                this.fenceBlockWithRenderType(fenceBlock, texture, CUTOUT);
                this.simpleBlockItem(fenceBlock, this.models().fenceInventory(this.name(fenceBlock), texture));
            } else if (block instanceof DoorBlockWithBase doorBlock) {
                String name = "block/" + this.name(doorBlock) + "_";
                this.doorBlockWithRenderType(doorBlock,
                        this.modLoc(name + "bottom"),
                        this.modLoc(name + "top"), CUTOUT);
            } else if (block instanceof SlabBlockWithBase slabBlock) {
                ResourceLocation texture = this.blockTexture(slabBlock.getBase());
                this.slabBlock(slabBlock, texture, texture);
            } else if (block instanceof WallBlockWithBase wallBlock) {
                ResourceLocation texture = this.blockTexture(wallBlock.getBase());
                this.wallBlock(wallBlock, texture);
                this.simpleBlockItem(wallBlock, this.models().wallInventory(this.name(wallBlock), texture));
            } else if (block instanceof FlowerPotBlock flowerPotBlock) {
                this.registerPottedPlantStates(flowerPotBlock, flowerPotBlock.getPotted());
            } else if (block.properties() instanceof NTBlockProperties properties && properties.isSimpleModelBlock) {
                this.simpleBlock(block);
            }
        }
    }

    private void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        this.simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

    private void registerCoralBlockStates(Block currentBlock, Block originalBlock) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().cubeAll(this.name(currentBlock), texture);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerCoralStates(Block currentBlock, Block originalBlock) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().cross(this.name(currentBlock), texture).renderType(CUTOUT);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerCoralFanStates(Block currentBlock, Block originalBlock, String parent) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().singleTexture(this.name(currentBlock),
                this.mcLoc("block/" + parent), ("fan"), texture).renderType(CUTOUT);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerCropStates(Block block, IntegerProperty property) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Integer stage : property.getPossibleValues()) {
            String name = this.name(block) + "_stage" + stage;
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().crop(name, texture).renderType(CUTOUT);
            builder.partialState().with(property, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerPapyrusStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Boolean value : Papyrus.TOP.getPossibleValues()) {
            String name = this.name(block) + (value ? "_top" : "_plant");
            ResourceLocation parent = this.mcLoc("block/tinted_cross");
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().singleTexture(name, parent, "cross", texture).renderType(CUTOUT);
            builder.partialState().with(Papyrus.TOP, value).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerPottedPlantStates(Block block, Block content) {
        this.simpleBlock(block, this.models().withExistingParent(this.name(block), "block/flower_pot_cross")
                .renderType(CUTOUT).texture("plant", this.blockTexture(content)));
    }

    private void registerPlantStates(Block block) {
        this.simpleBlock(block, this.models().cross(this.name(block), this.blockTexture(block)).renderType(CUTOUT));
    }

    private void registerDoublePlantStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (DoubleBlockHalf half : DoublePlantBlock.HALF.getPossibleValues()) {
            String name = this.name(block) + "_" + half.toString();
            ResourceLocation parent = this.mcLoc("block/tinted_cross");
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().withExistingParent(name, parent)
                    .texture("cross", texture).renderType(CUTOUT);
            builder.partialState().with(DoublePlantBlock.HALF, half)
                    .modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerHarmoniousChangeStoveStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (HCStovePart part : HarmoniousChangeStove.PART.getPossibleValues()) {
            for (DoubleBlockHalf half : HarmoniousChangeStove.HALF.getPossibleValues()) {
                for (Direction direction : HarmoniousChangeStove.FACING.getPossibleValues()) {
                    for (Boolean lit : HarmoniousChangeStove.LIT.getPossibleValues()) {
                        String name = "harmonious_change_stove_" + (lit ? "on" : "off");
                        ResourceLocation parent = this.modLoc("block/harmonious_change_stove");
                        ResourceLocation texture = this.modLoc("block/" + name);
                        ModelFile modelFile = this.models().withExistingParent(name, parent)
                                .texture("0", texture).texture("particle", texture).renderType(CUTOUT);
                        builder.partialState().with(HarmoniousChangeStove.PART, part)
                                .with(HarmoniousChangeStove.HALF, half)
                                .with(HarmoniousChangeStove.FACING, direction)
                                .with(HarmoniousChangeStove.LIT, lit).modelForState()
                                .rotationY(DIRECTION_WITH_ROTATION.get(direction))
                                .modelFile(modelFile).addModel();
                    }
                }
            }
        }
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}