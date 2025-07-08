package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.entities.animal.MooBloom;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MooBloomStrangeFeed extends Item {

    public MooBloomStrangeFeed() {
        super(new Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Cow cow) {
            MooBloom mooBloom = cow.convertTo(NTEntityTypes.MOO_BLOOM.get(), Boolean.FALSE);
            if (mooBloom != null) {
                if (!mooBloom.level().isClientSide) {
                    mooBloom.setHealth(cow.getHealth());
                    stack.consume(1, player);
                } else {
                    this.spawnHeartParticles(mooBloom);
                }
            }
        }

        stack.consume(1, player);
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