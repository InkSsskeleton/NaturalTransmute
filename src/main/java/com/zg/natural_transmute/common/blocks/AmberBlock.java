package com.zg.natural_transmute.common.blocks;

import com.mojang.serialization.MapCodec;
import com.zg.natural_transmute.common.blocks.entity.AmberBlockBlockEntity;
import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AmberBlock extends HalfTransparentBlock implements EntityBlock {

    public AmberBlock() {
        super(NTBlockProperties.ofFullCopy(Blocks.STONE).useSimpleBlockItem().noOcclusion().lightLevel(l -> 4));
    }

    @Override
    protected MapCodec<? extends AmberBlock> codec() {
        return simpleCodec(p -> new AmberBlock());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof AmberBlockBlockEntity amberBlock) {
            ItemStack item = amberBlock.getItem(0);
            if (!item.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), item);
            }

            amberBlock.setItem(0, stack);
            amberBlock.setChanged();
            stack.consume(1, player);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AmberBlockBlockEntity amberBlock) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), amberBlock.getItem(0));
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AmberBlockBlockEntity(pos, state);
    }

}