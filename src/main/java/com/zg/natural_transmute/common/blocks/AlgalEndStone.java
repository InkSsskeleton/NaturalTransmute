package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class AlgalEndStone extends Block {

    public static final BooleanProperty WITHERED = BooleanProperty.create("withered");

    public AlgalEndStone() {
        super(NTBlockProperties.ofFullCopy(Blocks.END_STONE).isSimpleModelBlock().useSimpleBlockItem());
        this.registerDefaultState(this.stateDefinition.any().setValue(WITHERED, Boolean.TRUE));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(WITHERED)) {
            level.setBlock(pos, Blocks.END_STONE.defaultBlockState(), 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WITHERED)) {
            level.scheduleTick(currentPos, this, 600 + level.getRandom().nextInt(100));
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        if (state.hasProperty(WITHERED) && state.getValue(WITHERED)) {
            level.scheduleTick(clickedPos, this, 600 + level.getRandom().nextInt(100));
        }

        return this.defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WITHERED);
    }

}