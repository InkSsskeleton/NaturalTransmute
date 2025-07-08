package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.items.crafting.*;
import com.zg.natural_transmute.common.items.crafting.special.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NaturalTransmute.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GatheringRecipe>> GATHERING_SERIALIZER =
            RECIPE_SERIALIZERS.register("gathering", () -> new GatheringSerializer<>(GatheringRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HarmoniousChangeRecipe>> HARMONIOUS_CHANGE_SERIALIZER =
            RECIPE_SERIALIZERS.register("harmonious_change", HarmoniousChangeSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCBlockFamilyTransferRecipe>> HC_BLOCK_FAMILY_TRANSFER_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_block_family_transfer", HCBlockFamilyTransferSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCUnglazedTerracottaRecipe>> HC_UNGLAZED_TERRACOTTA_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_unglazed_terracotta", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCUnglazedTerracottaRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCInactivateCoralRecipe>> HC_INACTIVATE_CORAL_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_inactivate_coral", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCInactivateCoralRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCActivateCoralRecipe>> HC_ACTIVATE_CORAL_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_activate_coral", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCActivateCoralRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCCreateInfestedBlockRecipe>> HC_CREATE_INFESTED_BLOCK_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_create_infested_block", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCCreateInfestedBlockRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCRecycleInfestedBlockRecipe>> HC_RECYCLE_INFESTED_BLOCK_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_recycle_infested_block", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCRecycleInfestedBlockRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCRefrigeratedRocketRecipe>> HC_REFRIGERATED_ROCKET_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_refrigerated_rocket", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCRefrigeratedRocketRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCMelodiousDiscRecipe>> HC_MELODIOUS_DISC_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_melodious_disc", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCMelodiousDiscRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCLeaderBannerRecipe>> HC_LEADER_BANNER_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_leader_banner", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCLeaderBannerRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCSuspiciousStewRecipe>> HC_SUSPICIOUS_STEW_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_suspicious_stew", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCSuspiciousStewRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCCopperOxidationRecipe>> HC_COPPER_OXIDATION_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_copper_oxidation", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCCopperOxidationRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCCopperDerustingRecipe>> HC_COPPER_DERUSTING_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_copper_derusting", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCCopperDerustingRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCPlantToWheatRecipe>> HC_PLANT_TO_WHEAT_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_plant_to_wheat", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCPlantToWheatRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCPlantToGlowLichenRecipe>> HC_PLANT_TO_GLOW_LICHEN_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_plant_to_glow_lichen", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCPlantToGlowLichenRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCHeroicEnchantedBookRecipe>> HC_HEROIC_ENCHANTED_BOOK_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_heroic_enchanted_book", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCHeroicEnchantedBookRecipe()));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HCSculkCatalystRecipe>> HC_SCULK_CATALYST_SERIALIZER =
            RECIPE_SERIALIZERS.register("hc_sculk_catalyst", () -> new HCMetaphysicaOnlySerializer<>(ingredient -> new HCSculkCatalystRecipe()));

}