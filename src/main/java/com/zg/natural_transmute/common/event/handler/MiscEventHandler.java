package com.zg.natural_transmute.common.event.handler;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTMobEffects;
import com.zg.natural_transmute.utils.HarmoniousChangeFuelUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID)
public class MiscEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = NTMobEffects.VULNERABLE;
        MobEffectInstance instance = entity.getEffect(effect);
        for (ItemStack stack : entity.getArmorSlots()) {
            boolean b1 = event.getSource().is(DamageTypes.MAGIC);
            boolean b2 = event.getSource().is(DamageTypes.INDIRECT_MAGIC);
            if (stack.has(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE) && (b1 || b2)) {
                event.setNewDamage(0.0F); break;
            }
        }

        if (entity.hasEffect(effect) && instance != null) {
            int amplifier = instance.getAmplifier();
            float damage = event.getOriginalDamage();
            event.setNewDamage(damage + Math.min(amplifier, 4.0F));
        }
    }

    @SubscribeEvent
    public static void onEntityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
        boolean b1 = event.getOriginalInvulnerability();
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            boolean b2 = event.getSource() == itemEntity.level().damageSources().cactus();
            if (itemEntity.getItem().is(NTItems.PITAYA) && (b1 || b2)) {
                event.setInvulnerable(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        ItemStack stack = event.getItemStack();
        if (!level.isClientSide && stack.is(NTItems.ANNIHILATE_ROD)) {
            EquipmentSlot slot = LivingEntity.getSlotForHand(event.getHand());
            BlockState state = level.getBlockState(event.getPos());
            float destroyTime = state.getBlock().defaultDestroyTime();
            if (destroyTime < 0.0F) destroyTime = 10.0F;
            stack.hurtAndBreak(Mth.ceil(destroyTime), event.getEntity(), slot);
            level.destroyBlock(event.getPos(), Boolean.FALSE);
        }
    }

    @SubscribeEvent
    public static void onArmorHurt(ArmorHurtEvent event) {
        double totalProbability = 1.0D;
        int armorCount = 0;
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        for (EquipmentSlot slot : event.getArmorMap().keySet()) {
            ItemStack stack = event.getArmorItemStack(slot);
            if (stack.has(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE)) {
                totalProbability *= 0.75F;
                armorCount++;
            }
        }

        if (!level.isClientSide && Math.random() < 1.0F - totalProbability && armorCount > 0) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(level, entity.getX(), entity.getY(), entity.getZ());
            areaEffectCloud.setParticle(ParticleTypes.DRAGON_BREATH);
            areaEffectCloud.setRadius(3.0F);
            areaEffectCloud.setDuration(600);
            areaEffectCloud.setRadiusPerTick((7.0F - areaEffectCloud.getRadius()) / areaEffectCloud.getDuration());
            areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
            level.levelEvent(2006, entity.blockPosition(), entity.isSilent() ? -1 : 1);
            level.addFreshEntity(areaEffectCloud);
        }
    }

}