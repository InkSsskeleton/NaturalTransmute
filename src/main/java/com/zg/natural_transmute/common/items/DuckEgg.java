package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.projectile.ThrownDuckEggEntity;
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

public class DuckEgg extends Item implements ProjectileItem {

    public DuckEgg() {
        super(new Properties().stacksTo(16).component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        float pitch = 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, pitch);
        if (!level.isClientSide) {
            ThrownDuckEggEntity thrownDuckEgg = new ThrownDuckEggEntity(level, player);
            thrownDuckEgg.setItem(itemInHand);
            thrownDuckEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownDuckEgg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemInHand.consume(1, player);
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownDuckEggEntity thrownDuckEgg = new ThrownDuckEggEntity(level, pos.x(), pos.y(), pos.z());
        thrownDuckEgg.setItem(stack);
        return thrownDuckEgg;
    }

}