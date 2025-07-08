package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.components.CatFoods;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class StrangeCatFood extends Item {

    private final @Nullable ResourceKey<CatVariant> variant;

    public StrangeCatFood(@Nullable ResourceKey<CatVariant> variant) {
        super(new Item.Properties()
                .component(NTDataComponents.CAT_FOODS, new CatFoods(variant))
                .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE));
        this.variant = variant;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Cat cat) {
            if (!cat.level().isClientSide) {
                float health = cat.getHealth();
                if (health < cat.getMaxHealth()) {
                    cat.heal((4.0F));
                }

                if (this.variant != null) {
                    cat.setVariant(BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(this.variant));
                } else {
                    Ocelot ocelot = cat.convertTo(EntityType.OCELOT, Boolean.FALSE);
                    if (ocelot != null) {
                        float maxHealth = ocelot.getMaxHealth();
                        ocelot.setHealth(Math.max(health, maxHealth));
                    }
                }

                stack.consume(1, player);
            }

            return InteractionResult.sidedSuccess(cat.level().isClientSide);
        }

        return InteractionResult.CONSUME;
    }

}