package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.projectile.SilverfishPupaEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class SilverfishPupa extends Item implements ProjectileItem {

    public SilverfishPupa() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        float pitch = 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F);
        ItemStack itemStack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, pitch);
        if (!level.isClientSide) {
            SilverfishPupaEntity silverfishPupa = new SilverfishPupaEntity(level, player);
            silverfishPupa.setItem(itemStack);
            silverfishPupa.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(silverfishPupa);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        SilverfishPupaEntity silverfishPupa = new SilverfishPupaEntity(level, pos.x(), pos.y(), pos.z());
        silverfishPupa.setItem(stack);
        return silverfishPupa;
    }

}