package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MooshroomStrangeFeed extends Item {

    public MooshroomStrangeFeed() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Cow cow) {
            MushroomCow mushroomCow = cow.convertTo(EntityType.MOOSHROOM, Boolean.FALSE);
            stack.consume(1, player);
            if (mushroomCow != null) {
                if (!cow.level().isClientSide) {
                    mushroomCow.setVariant(MushroomCow.MushroomType.RED);
                } else {
                    this.spawnHeartParticles(cow);
                }
            }
        } else if (interactionTarget instanceof MushroomCow mushroomCow) {
            stack.consume(1, player);
            if (!mushroomCow.level().isClientSide) {
                if (mushroomCow.getVariant() == MushroomCow.MushroomType.RED) {
                    mushroomCow.setVariant(MushroomCow.MushroomType.BROWN);
                } else {
                    mushroomCow.setVariant(MushroomCow.MushroomType.RED);
                }
            } else {
                this.spawnHeartParticles(mushroomCow);
            }
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    protected void spawnHeartParticles(LivingEntity entity) {
        ParticleOptions options = ParticleTypes.HEART;
        for (int i = 0; i < 7; i++) {
            double d0 = entity.getRandom().nextGaussian() * 0.02D;
            double d1 = entity.getRandom().nextGaussian() * 0.02D;
            double d2 = entity.getRandom().nextGaussian() * 0.02D;
            double x = entity.getRandomX(1.0D);
            double y = entity.getRandomY() + 0.5D;
            double z = entity.getRandomZ(1.0D);
            entity.level().addParticle(options, x, y, z, d0, d1, d2);
        }
    }

}