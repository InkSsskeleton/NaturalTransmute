package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import com.zg.natural_transmute.common.data.tags.NTBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PeatMoss extends CarpetBlock {

    public PeatMoss() {
        super(NTBlockProperties.ofFullCopy(Blocks.MOSS_CARPET).useSimpleBlockItem());
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return state.is(NTBlockTags.PEAT_MOSS_PLACEABLE);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.tickCount % 100 == 0) {
                livingEntity.heal(1.0F);
            }
        }
    }

}