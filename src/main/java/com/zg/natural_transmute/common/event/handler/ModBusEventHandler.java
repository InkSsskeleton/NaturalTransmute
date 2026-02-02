package com.zg.natural_transmute.common.event.handler;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.gui.GatheringPlatformScreen;
import com.zg.natural_transmute.client.gui.HarmoniousChangeStoveScreen;
import com.zg.natural_transmute.client.model.entity.DuckModel;
import com.zg.natural_transmute.client.renderer.block.AmberBlockRenderer;
import com.zg.natural_transmute.client.renderer.block.GatheringPlatformRenderer;
import com.zg.natural_transmute.client.renderer.entity.BreezeArrowRenderer;
import com.zg.natural_transmute.client.renderer.entity.DuckRenderer;
import com.zg.natural_transmute.client.renderer.entity.LavaAxolotlRenderer;
import com.zg.natural_transmute.client.renderer.entity.MooBloomRenderer;
import com.zg.natural_transmute.common.entities.animal.LavaAxolotl;
import com.zg.natural_transmute.registry.*;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler {

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(NTDataMaps.HARMONIOUS_CHANGE_FUELS);
    }

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        NTBlocks.END_ALSOPHILA_FAMILY.forEach(holder -> fireBlock.setFlammable(holder.get(), (5), (20)));
        fireBlock.setFlammable(NTBlocks.END_ALSOPHILA_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(NTBlocks.END_ALSOPHILA_LOG.get(), 5, 5);
        fireBlock.setFlammable(NTBlocks.END_ALSOPHILA_WOOD.get(), 5, 5);
        fireBlock.setFlammable(NTBlocks.END_ALSOPHILA_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(NTBlocks.PLANTAIN_STEM.get(), 20, 20);
        fireBlock.setFlammable(NTBlocks.PLANTAIN_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(NTBlocks.REED.get(), 60, 100);
    }

    @SubscribeEvent
    public static void registerDispenserBehavior(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerProjectileBehavior(NTItems.DUCK_EGG.get());
            DispenserBlock.registerProjectileBehavior(NTItems.BREEZE_ARROW.get());
            DispenserBlock.registerProjectileBehavior(NTItems.REFRIGERATED_ROCKET.get());
            DispenserBlock.registerBehavior(NTItems.MINE_WATER_BUCKET.get(), new DispenseBucket());
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(NTMenus.GATHERING_PLATFORM.get(), GatheringPlatformScreen::new);
        event.register(NTMenus.HARMONIOUS_CHANGE_STOVE.get(), HarmoniousChangeStoveScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(NTEntityTypes.LAVA_AXOLOTL.get(), LavaAxolotl.createAttributes().build());
        event.put(NTEntityTypes.MOO_BLOOM.get(), MushroomCow.createAttributes().build());
        event.put(NTEntityTypes.DUCK.get(), Chicken.createAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NTBlockEntityTypes.GATHERING_PLATFORM.get(), GatheringPlatformRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.AMBER_BLOCK.get(), AmberBlockRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.BREEZE_ARROW.get(), BreezeArrowRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.SILVERFISH_PUPA.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.REFRIGERATED_ROCKET.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.LAVA_AXOLOTL.get(), LavaAxolotlRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.MOO_BLOOM.get(), MooBloomRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(NTEntityTypes.DUCK_EGG.get(), ThrownItemRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NTModelLayers.GATHERING_PLATFORM, GatheringPlatformRenderer::createBodyLayer);
        event.registerLayerDefinition(NTModelLayers.MOO_BLOOM, CowModel::createBodyLayer);
        event.registerLayerDefinition(NTModelLayers.DUCK, DuckModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(NTItems.WHALE_BONE_BOW.get(), NaturalTransmute.prefix("pull"), ((stack, level, entity, seed) ->
                    entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F));
            ItemProperties.register(NTItems.WHALE_BONE_BOW.get(), NaturalTransmute.prefix("pulling"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            ItemProperties.register(NTItems.PETARD.get(), NaturalTransmute.prefix("ignited"), ((stack, level, entity, seed) -> {
                Boolean ignited = stack.get(NTDataComponents.PETARD_IGNITED.get());
                return ignited != null && ignited ? 1.0F : 0.0F;
            }));
        });
    }

    @MethodsReturnNonnullByDefault
    private static class DispenseBucket extends DefaultDispenseItemBehavior {

        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem containerItem = (DispensibleContainerItem) stack.getItem();
            BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            if (containerItem.emptyContents(null, source.level(), blockPos, null, stack)) {
                containerItem.checkExtraContent(null, source.level(), stack, blockPos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }

    }

}