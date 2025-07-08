package com.zg.natural_transmute;

import com.zg.natural_transmute.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import java.util.Locale;

@Mod(NaturalTransmute.MOD_ID)
public class NaturalTransmute {

    public static final String MOD_ID = "natural_transmute";

    public NaturalTransmute(IEventBus modEventBus) {
        NTItems.ITEMS.register(modEventBus);
        NTBlocks.BLOCKS.register(modEventBus);
        NTMenus.MENUS.register(modEventBus);
        NTFluids.FLUIDS.register(modEventBus);
        NTFluidTypes.FLUID_TYPES.register(modEventBus);
        NTMobEffects.MOB_EFFECTS.register(modEventBus);
        NTRecipes.RECIPE_TYPES.register(modEventBus);
        NTRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        NTEntityTypes.ENTITY_TYPES.register(modEventBus);
        NTTriggerTypes.TRIGGER_TYPES.register(modEventBus);
        NTAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        NTBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        NTDataComponents.DATA_COMPONENT_TYPE.register(modEventBus);
        NTCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        NTFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modEventBus);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}