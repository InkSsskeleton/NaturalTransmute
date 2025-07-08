package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.projectile.ThrownPetard;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class Petard extends Item implements ProjectileItem {

    private static final DataComponentType<Boolean> IGNITED = NTDataComponents.PETARD_IGNITED.get();
    private static final DataComponentType<Integer> FUSE = NTDataComponents.PETARD_FUSE.get();

    public Petard(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        Boolean ignited = itemInHand.get(IGNITED);
        Integer fuse = itemInHand.get(FUSE);
        if (ignited != null && !ignited) {
            itemInHand.set(IGNITED, true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS);
            return InteractionResultHolder.pass(itemInHand);
        }

        float pitch = 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, pitch);
        if (!level.isClientSide && fuse != null) {
            ThrownPetard thrownPetard = new ThrownPetard(level, player);
            thrownPetard.setItem(itemInHand);
            thrownPetard.setFuse(fuse);
            thrownPetard.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownPetard);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemInHand.consume(1, player);
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide) {
            Boolean ignited = stack.get(IGNITED);
            Integer fuse = stack.get(FUSE);
            if (ignited != null && fuse != null && ignited) {
                stack.set(FUSE, Math.max(0, fuse - 1));
                if (fuse <= 0) {
                    entity.level().explode(null, entity.getX(), entity.getY(), entity.getZ(), 3.0F, Level.ExplosionInteraction.TNT);
                    stack.setCount(0);
                }
            }
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownPetard thrownDuckEgg = new ThrownPetard(level, pos.x(), pos.y(), pos.z());
        thrownDuckEgg.setItem(stack);
        return thrownDuckEgg;
    }

}