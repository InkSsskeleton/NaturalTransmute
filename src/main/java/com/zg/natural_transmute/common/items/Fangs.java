package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Fangs extends Item {

    public Fangs() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (!level.isClientSide()) {
            Vec3 viewVector = player.getViewVector(1.0F).scale(16.0D);
            Vec3 vec3 = player.getEyePosition().add(viewVector);
            double f = Mth.atan2(vec3.z - player.getZ(), vec3.x - player.getX());
            for (int l = 0; l < 16; l++) {
                double d2 = 1.25D * (double) (l + 1);
                double x = player.getX() + Math.cos(f) * d2;
                double z = player.getZ() + Math.sin(f) * d2;
                this.createSpellEntity(level, player, x, z, player.getY(), player.getY(), (float) f, l);
            }
        }

        itemInHand.consume(1, player);
        return InteractionResultHolder.success(itemInHand);
    }

    private void createSpellEntity(Level level, Player player, double x, double z, double minY, double maxY, float yRot, int warmupDelay) {
        BlockPos containingPos = BlockPos.containing(x, maxY, z);
        double d0 = 0.0D;
        do {
            BlockPos blockPos = containingPos.below();
            if (level.getBlockState(blockPos).isFaceSturdy(level, blockPos, Direction.UP)) {
                if (!level.isEmptyBlock(containingPos)) {
                    BlockState blockState = level.getBlockState(containingPos);
                    VoxelShape voxelShape = blockState.getCollisionShape(level, containingPos);
                    if (!voxelShape.isEmpty()) {
                        d0 = voxelShape.max(Direction.Axis.Y);
                    }
                }

                break;
            }

            containingPos = containingPos.below();
        } while (containingPos.getY() >= Mth.floor(minY) - 1);
        level.addFreshEntity(new EvokerFangs(level, x, containingPos.getY() + d0, z, yRot, warmupDelay, player));
        level.gameEvent(GameEvent.ENTITY_PLACE, new Vec3(x, containingPos.getY() + d0, z), GameEvent.Context.of(player));
    }

}