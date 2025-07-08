package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.components.DogFoods;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StrangeDogFood extends Item {

    private final ResourceKey<WolfVariant> variant;

    public StrangeDogFood(ResourceKey<WolfVariant> variant) {
        super(new Item.Properties()
                .component(NTDataComponents.DOG_FOODS, new DogFoods(variant))
                .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
        this.variant = variant;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Wolf wolf) {
            Level level = wolf.level();
            if (!level.isClientSide) {
                HolderLookup.RegistryLookup<WolfVariant> registryLookup = level.registryAccess().lookupOrThrow(Registries.WOLF_VARIANT);
                wolf.setVariant(registryLookup.getOrThrow(this.variant));
                if (wolf.getHealth() < wolf.getMaxHealth()) {
                    wolf.heal((4.0F));
                }

                if (!player.getAbilities().instabuild) {
                    stack.consume(1, player);
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.CONSUME;
    }

}