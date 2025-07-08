package com.zg.natural_transmute.common.items;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.zg.natural_transmute.common.blocks.ActivatedCoralBlock;
import com.zg.natural_transmute.common.blocks.ActivatedCoralFan;
import com.zg.natural_transmute.common.blocks.ActivatedCoralPlant;
import com.zg.natural_transmute.common.blocks.ActivatedCoralWallFan;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class WaterWax extends HoneycombItem {

    public static final BooleanProperty NO_DIE = BooleanProperty.create("no_die");
    public static final Supplier<BiMap<Block, Block>> CAN_USE_WATER_WAX = Suppliers.memoize(WaterWax::putCoralAndDeadCoral);

    public WaterWax() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(clickedPos);
        if (WAXABLES.get().containsKey(blockState.getBlock())) {
            return super.useOn(context);
        }

        return getCoralWaxed(blockState).map(state -> {
            Player player = context.getPlayer();
            ItemStack itemInHand = context.getItemInHand();
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, clickedPos, itemInHand);
            }

            itemInHand.shrink(1);
            level.setBlock(clickedPos, state.setValue(NO_DIE, Boolean.TRUE), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, clickedPos, GameEvent.Context.of(player, state));
            level.levelEvent(player, 3003, clickedPos, 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }).orElse(InteractionResult.PASS);
    }

    public static Optional<BlockState> getCoralWaxed(BlockState state) {
        return Optional.ofNullable(CAN_USE_WATER_WAX.get().get(state.getBlock())).map(block -> block.withPropertiesOf(state));
    }

    private static ImmutableBiMap<Block, Block> putCoralAndDeadCoral() {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        NTCommonUtils.getKnownBlocks().forEach(block -> {
            if (block instanceof ActivatedCoralBlock coral) {
                builder.put(coral.deadBlock, coral);
            } else if (block instanceof ActivatedCoralFan coralFan) {
                builder.put(coralFan.deadBlock, coralFan);
            } else if (block instanceof ActivatedCoralPlant coralPlant) {
                builder.put(coralPlant.deadBlock, coralPlant);
            } else if (block instanceof ActivatedCoralWallFan coralWallFan) {
                builder.put(coralWallFan.deadBlock, coralWallFan);
            }
        });

        return builder.build();
    }

}