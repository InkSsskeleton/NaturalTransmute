package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.projectile.BreezeArrowEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BreezeArrow extends ArrowItem {

    public BreezeArrow() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
         return new BreezeArrowEntity(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BreezeArrowEntity breezeArrow = new BreezeArrowEntity(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), (null));
        breezeArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return breezeArrow;
    }

}