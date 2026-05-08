package com.zg.natural_transmute.common.blocks;

import com.mojang.serialization.MapCodec;
import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class HarmoniousChangeStove extends BaseEntityBlockWithState {

    public static final EnumProperty<HCStovePart> PART = EnumProperty.create("part", HCStovePart.class);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public HarmoniousChangeStove() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().pushReaction(PushReaction.IGNORE));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, HCStovePart.MAIN_BLOCK)
                .setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends HarmoniousChangeStove> codec() {
        return simpleCodec(p -> new HarmoniousChangeStove());
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.sidedSuccess(true);
        }

        var facing = state.getValue(FACING);
        if (player instanceof ServerPlayer serverPlayer) {
            var part = state.getValue(PART);
            BlockPos mainPos;
            if (!part.isMainBlock()) {
                mainPos = pos.offset(part.getRelativeMainPos(facing));
            } else {
                mainPos = pos;
            }
            serverPlayer.openMenu(state.getMenuProvider(level, mainPos), extraData -> extraData.writeBlockPos(mainPos));
            return InteractionResult.sidedSuccess(false);
        }

        return InteractionResult.PASS;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            var facing = state.getValue(FACING);
            var part = state.getValue(PART);
            var mainPos = pos.offset(part.getRelativeMainPos(facing));
            for (var p : HCStovePart.values()) {
                var partPos = mainPos.offset(p.getRelativePos(facing));
                var partState = level.getBlockState(partPos);
                if (isValidBlock(partState)) {
                    var be = level.getBlockEntity(partPos);
                    if (be != null) {
                        be.setRemoved();
                    }
                    level.destroyBlock(partPos, p.isMainBlock() && !player.isCreative(), null);
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level.isClientSide) {
            return;
        }

        if (!(newState.getBlock() instanceof HarmoniousChangeStove)) {
            var part = state.getValue(PART);
            var facing = state.getValue(FACING);
            var mainPos = pos.offset(part.getRelativeMainPos(facing));
            var be = level.getBlockEntity(mainPos);
            if (be instanceof HarmoniousChangeStoveBlockEntity blockEntity) {
                Containers.dropContents(level, mainPos, blockEntity);
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var facing = context.getHorizontalDirection().getOpposite();
        if (!canPlace(level, pos, facing)) {
            return null;
        }
        return defaultBlockState()
                .setValue(FACING, facing)
                .setValue(PART, HCStovePart.MAIN_BLOCK);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.isClientSide) {
            return;
        }

        var facing = state.getValue(FACING);
        for (var p : HCStovePart.values()) {
            if (p.isMainBlock()) {
                continue;
            }

            var partPos = pos.offset(p.getRelativePos(facing));
            level.setBlock(partPos, defaultBlockState()
                    .setValue(FACING, facing)
                    .setValue(PART, p), Block.UPDATE_ALL);
        }
    }

    private boolean canPlace(Level level, BlockPos pos, Direction facing) {
        if (!level.isAreaLoaded(pos, 2)) {
            return false;
        }

        for (var p : HCStovePart.values()) {
            var partPos = pos.offset(p.getRelativePos(facing));
            if (!level.getBlockState(partPos).canBeReplaced()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, LIT);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART).isMainBlock() ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HarmoniousChangeStoveBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), HarmoniousChangeStoveBlockEntity::serverTick);
    }

    private boolean isValidBlock(BlockState state) {
        return state.is(this);
    }

    private boolean isStructureStillValid(LevelAccessor level, BlockPos pos, BlockState state) {
        var facing = state.getValue(FACING);
        var part = state.getValue(PART);
        var mainPos = pos.offset(part.getRelativeMainPos(facing));
        for (var p : HCStovePart.values()) {
            var partPos = mainPos.offset(p.getRelativePos(facing));
            var partState = level.getBlockState(partPos);
            if (!isValidBlock(partState)) {
                return false;
            }
        }
        return true;
    }
}