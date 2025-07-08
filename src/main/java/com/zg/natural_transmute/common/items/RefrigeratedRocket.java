package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.projectile.RefrigeratedRocketEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RefrigeratedRocket extends FireworkRocketItem {

    public RefrigeratedRocket() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            ItemStack itemInHand = context.getItemInHand();
            Vec3 vec3 = context.getClickLocation();
            Direction direction = context.getClickedFace();
            RefrigeratedRocketEntity entity = new RefrigeratedRocketEntity(
                    level, context.getPlayer(),
                    vec3.x + direction.getStepX() * 0.15D,
                    vec3.y + direction.getStepY() * 0.15D,
                    vec3.z + direction.getStepZ() * 0.15D,
                    itemInHand);
            level.addFreshEntity(entity);
            itemInHand.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isFallFlying()) {
            ItemStack itemInHand = player.getItemInHand(hand);
            if (!level.isClientSide) {
                RefrigeratedRocketEntity entity = new RefrigeratedRocketEntity(level, itemInHand, player);
                level.addFreshEntity(entity);
                itemInHand.consume(1, player);
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new RefrigeratedRocketEntity(level, stack.copyWithCount(1), pos.x(), pos.y(), pos.z(), Boolean.TRUE);
    }

}