package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.items.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.zg.natural_transmute.utils.NTItemRegUtils.*;

public class NTItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Item, Item> AMBER = normal("amber");
    public static final DeferredHolder<Item, Item> BERYL = normal("beryl");
    public static final DeferredHolder<Item, Item> CHLORITE = normal("chlorite");
    public static final DeferredHolder<Item, Item> COCONUT_SHELL = normal("coconut_shell");
    public static final DeferredHolder<Item, Item> CORUNDUM_IRON_PLATE = normal("corundum_iron_plate");
    public static final DeferredHolder<Item, Item> DRAGONCAST_STEEL_BILLET = normal("dragoncast_steel_billet");
    public static final DeferredHolder<Item, Item> DRAGONCAST_STEEL_INGOT = normal("dragoncast_steel_ingot");
    public static final DeferredHolder<Item, Item> DRYAS_OCTOPETALA = normal("dryas_octopetala");
    public static final DeferredHolder<Item, Item> GANODERMA_LUCIDUM = normal("ganoderma_lucidum");
    public static final DeferredHolder<Item, Item> GREEN_GHOST_CRYSTAL = normal("green_ghost_crystal");
    public static final DeferredHolder<Item, Item> HAIR_CRYSTAL = normal("hair_crystal");
    public static final DeferredHolder<Item, Item> HARMONIOUS_CHANGE_CORE = normal("harmonious_change_core");
    public static final DeferredHolder<Item, Item> HARMONIOUS_CHANGE_FUEL = normal("harmonious_change_fuel");
    public static final DeferredHolder<Item, Item> HETEROGENEOUS_STONE = normal("heterogeneous_stone");
    public static final DeferredHolder<Item, Item> HELIODOR = normal("heliodor");
    public static final DeferredHolder<Item, Item> ICELAND_SPAR = normal("iceland_spar");
    public static final DeferredHolder<Item, Item> MALACHITE = normal("malachite");
    public static final DeferredHolder<Item, Item> RED_BERYL = normal("red_beryl");
    public static final DeferredHolder<Item, Item> SCULK_BONE = normal("sculk_bone");
    public static final DeferredHolder<Item, Item> TRUFFLE = normal("truffle");
    public static final DeferredHolder<Item, Item> WHALE_BONE = normal("whale_bone");
    public static final DeferredHolder<Item, Item> WITHER_BONE = normal("wither_bone");
    public static final DeferredHolder<Item, Item> TRANSPARENT_CRYSTAL = normal("transparent_crystal");
    public static final DeferredHolder<Item, Item> FANGS = ITEMS.register("fangs", Fangs::new);
    public static final DeferredHolder<Item, Item> WATER_WAX = ITEMS.register("water_wax", WaterWax::new);
    public static final DeferredHolder<Item, Item> BREEZE_ARROW = ITEMS.register("breeze_arrow", BreezeArrow::new);
    public static final DeferredHolder<Item, Item> SILVERFISH_PUPA =
            ITEMS.register("silverfish_pupa", SilverfishPupa::new);
    public static final DeferredHolder<Item, Item> REFRIGERATED_ROCKET =
            ITEMS.register("refrigerated_rocket", RefrigeratedRocket::new);
    public static final DeferredHolder<Item, Item> MOOSHROOM_STRANGE_FEED =
            ITEMS.register("mooshroom_strange_feed", MooshroomStrangeFeed::new);
    public static final DeferredHolder<Item, Item> MOO_BLOOM_STRANGE_FEED =
            ITEMS.register("moo_bloom_strange_feed", MooBloomStrangeFeed::new);
    public static final DeferredHolder<Item, Item> ANNIHILATE_ROD = ITEMS.register("annihilate_rod",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).durability((5))
                    .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> PETARD = ITEMS.register("petard",
            () -> new Petard(new Item.Properties().stacksTo(1)
                    .component(NTDataComponents.PETARD_IGNITED, false)
                    .component(NTDataComponents.PETARD_FUSE, 100)));
    public static final DeferredHolder<Item, Item> MINE_WATER_BUCKET = ITEMS.register("mine_water_bucket",
            () -> new BucketItem(NTFluids.MINE_WATER_STILL.get(), new Item.Properties().stacksTo((1))
                    .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> MELODIOUS_DISC = ITEMS.register("melodious_disc",
            () -> new Item(new Item.Properties().stacksTo((1))
                    .rarity(Rarity.RARE).jukeboxPlayable(NTJukeboxSongs.EMPTY)
                    .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> LAVA_AXOLOTL_BUCKET = ITEMS.register("lava_axolotl_bucket",
            () -> new MobBucketItem(NTEntityTypes.LAVA_AXOLOTL.get(), Fluids.LAVA, SoundEvents.BUCKET_EMPTY_AXOLOTL,
                    new Item.Properties().stacksTo((1))
                            .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> HARMONIOUS_CHANGE_LAVA_BUCKET =
            ITEMS.register("harmonious_change_lava_bucket",
                    () -> new Item(new Item.Properties().rarity(Rarity.RARE)
                            .durability(100).craftRemainder(Items.BUCKET)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> ETERNAL_HARMONIOUS_CHANGE_LAVA_BUCKET =
            ITEMS.register("eternal_harmonious_change_lava_bucket",
                    () -> new Item(new Item.Properties().rarity(Rarity.EPIC)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> DUCK_EGG = ITEMS.register("duck_egg", DuckEgg::new);
    public static final DeferredHolder<Item, Item> REED = alias("reed", NTBlocks.REED, new Item.Properties());
    public static final DeferredHolder<Item, Item> WARPED_WART = alias("warped_wart", NTBlocks.WARPED_WART, new Item.Properties());
    // 方块
    public static final DeferredHolder<Item, Item> PEAT_MOSS = ITEMS.register("peat_moss",
            () -> new BlockItem(NTBlocks.PEAT_MOSS.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLOWERING_BLUE_TARO_VINE = ITEMS.register("flowering_blue_taro_vine",
            () -> new FloweringBlueTaroVineItem(NTBlocks.BLUE_TARO_VINE.get(), new Item.Properties()));
    // 食物
    public static final DeferredHolder<Item, Item> BLUEBERRIES = alias("blueberries",
            NTBlocks.BLUEBERRY_BUSH, new Item.Properties().food(Foods.SWEET_BERRIES));
    public static final DeferredHolder<Item, Item> PAPYRUS = alias("papyrus", NTBlocks.PAPYRUS, new Item.Properties());
    public static final DeferredHolder<Item, Item> BOTTLE_OF_TEA = ITEMS.register("bottle_of_tea",
            () -> new BottleOfTea(new Item.Properties().stacksTo((1)).food(new FoodProperties.Builder()
                            .nutrition((0)).saturationModifier((0.0F)).usingConvertsTo(Items.GLASS_BOTTLE)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600), 1.0F).build())
                    .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> COCONUT = ITEMS.register("coconut", () -> new Item(
            new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE).food(new FoodProperties.Builder()
                    .nutrition((2)).saturationModifier((0.5F)).usingConvertsTo(COCONUT_SHELL.get()).build())));
    public static final DeferredHolder<Item, Item> GOOSE_BARNACLE = ITEMS.register("goose_barnacle",
            () -> new GooseBarnacle(new Item.Properties().food(new FoodProperties.Builder().nutrition((2)).saturationModifier((0.5F)).build())
                    .component(DataComponents.SUSPICIOUS_STEW_EFFECTS, FlowerBlock.makeEffectList(MobEffects.WATER_BREATHING, 5.0F))
                    .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                    .attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                            4.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())));
    public static final DeferredHolder<Item, Item> HONEY_WINE = ITEMS.register("honey_wine", HoneyWine::new);
    public static final DeferredHolder<Item, Item> TRUFFLE_SOUP = ITEMS.register("truffle_soup", () -> new Item(
            new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE).food(
                    new FoodProperties.Builder().nutrition((6)).saturationModifier((5.0F)).usingConvertsTo(Items.BOWL)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1.0F).build())));
    public static final DeferredHolder<Item, Item> PITAYA = ITEMS.register("pitaya", () -> new ItemNameBlockItem(
            NTBlocks.PITAYA.get(), new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)
            .food(new FoodProperties.Builder().nutrition((4)).saturationModifier((0.2F)).build())));
    public static final DeferredHolder<Item, Item> PLANTAIN = ITEMS.register("plantain", () -> new ItemNameBlockItem(
            NTBlocks.PLANTAIN.get(), new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)
            .food(new FoodProperties.Builder().nutrition((4)).saturationModifier((0.6F)).build())));
    public static final DeferredHolder<Item, Item> PINEAPPLE = ITEMS.register("pineapple", () -> new ItemNameBlockItem(
            NTBlocks.PINEAPPLE.get(), new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)
            .food(new FoodProperties.Builder().nutrition((6)).saturationModifier((0.3F)).build())));
    public static final DeferredHolder<Item, Item> DUCK = ITEMS.register("duck", () -> new Item(
            new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE).food(Foods.CHICKEN)));
    public static final DeferredHolder<Item, Item> COOKED_DUCK = ITEMS.register("cooked_duck", () -> new Item(
            new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                    .food(new FoodProperties.Builder().nutrition((6)).saturationModifier((0.6F))
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL), 1.0F).build())));
    public static final DeferredHolder<Item, Item> VODKA = ITEMS.register("vodka",
            () -> new Vodka(new Item.Properties().component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE).food(new FoodProperties.Builder()
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0F).build())));
    // 猫粮
    public static final DeferredHolder<Item, Item> CAT_FOOD_BLACK = catFood("cat_food_black", CatVariant.BLACK);
    public static final DeferredHolder<Item, Item> CAT_FOOD_BRITISH_SHORTHAIR =
            catFood("cat_food_british_shorthair", CatVariant.BRITISH_SHORTHAIR);
    public static final DeferredHolder<Item, Item> CAT_FOOD_CALICO = catFood("cat_food_calico", CatVariant.CALICO);
    public static final DeferredHolder<Item, Item> CAT_FOOD_JELLIE = catFood("cat_food_jellie", CatVariant.JELLIE);
    public static final DeferredHolder<Item, Item> CAT_FOOD_PERSIAN = catFood("cat_food_persian", CatVariant.PERSIAN);
    public static final DeferredHolder<Item, Item> CAT_FOOD_RAGDOLL = catFood("cat_food_ragdoll", CatVariant.RAGDOLL);
    public static final DeferredHolder<Item, Item> CAT_FOOD_RED = catFood("cat_food_red", CatVariant.RED);
    public static final DeferredHolder<Item, Item> CAT_FOOD_SIAMESE = catFood("cat_food_siamese", CatVariant.SIAMESE);
    public static final DeferredHolder<Item, Item> CAT_FOOD_TABBY = catFood("cat_food_tabby", CatVariant.TABBY);
    public static final DeferredHolder<Item, Item> CAT_FOOD_TUXEDO = catFood("cat_food_tuxedo", CatVariant.ALL_BLACK);
    public static final DeferredHolder<Item, Item> CAT_FOOD_WHITE = catFood("cat_food_white", CatVariant.WHITE);
    public static final DeferredHolder<Item, Item> CAT_FOOD_OCELOT = catFood("cat_food_ocelot", null);
    // 狗粮
    public static final DeferredHolder<Item, Item> DOGFOOD_ASHEN = dogFood("dogfood_ashen", WolfVariants.ASHEN);
    public static final DeferredHolder<Item, Item> DOGFOOD_BLACK = dogFood("dogfood_black", WolfVariants.BLACK);
    public static final DeferredHolder<Item, Item> DOGFOOD_CHESTNUT = dogFood("dogfood_chestnut", WolfVariants.CHESTNUT);
    public static final DeferredHolder<Item, Item> DOGFOOD_CLASSICS = dogFood("dogfood_classics", WolfVariants.PALE);
    public static final DeferredHolder<Item, Item> DOGFOOD_RUSTY = dogFood("dogfood_rusty", WolfVariants.RUSTY);
    public static final DeferredHolder<Item, Item> DOGFOOD_SNOWY = dogFood("dogfood_snowy", WolfVariants.SNOWY);
    public static final DeferredHolder<Item, Item> DOGFOOD_SPOTTED = dogFood("dogfood_spotted", WolfVariants.SPOTTED);
    public static final DeferredHolder<Item, Item> DOGFOOD_STRIPED = dogFood("dogfood_striped", WolfVariants.STRIPED);
    public static final DeferredHolder<Item, Item> DOGFOOD_WOODS = dogFood("dogfood_woods", WolfVariants.WOODS);
    // 熔渣
    public static final DeferredHolder<Item, Item> ANDESITE_SLAG = normal("andesite_slag");
    public static final DeferredHolder<Item, Item> BASALT_SLAG = normal("basalt_slag");
    public static final DeferredHolder<Item, Item> DEEPSLATE_SLAG = normal("deepslate_slag");
    public static final DeferredHolder<Item, Item> DIORITE_SLAG = normal("diorite_slag");
    public static final DeferredHolder<Item, Item> GRANITE_SLAG = normal("granite_slag");
    public static final DeferredHolder<Item, Item> MUD_SLAG = normal("mud_slag");
    public static final DeferredHolder<Item, Item> SANDSTONE_SLAG = normal("sandstone_slag");
    public static final DeferredHolder<Item, Item> TUFF_SLAG = normal("tuff_slag");
    // 工具和装备
    public static final DeferredHolder<Item, Item> SCULK_BONE_SWORD = ITEMS.register("sculk_bone_sword",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().component(NTDataComponents.SCULK_EQUIPMENT, Boolean.TRUE)
                    .attributes(SwordItem.createAttributes(Tiers.IRON, (3.0F), (-2.4F)))));
    public static final DeferredHolder<Item, Item> SCULK_BONE_SHOVEL = ITEMS.register("sculk_bone_shovel",
            () -> new ShovelItem(Tiers.IRON, new Item.Properties().component(NTDataComponents.SCULK_EQUIPMENT, Boolean.TRUE)
                    .attributes(ShovelItem.createAttributes(Tiers.IRON, (1.5F), (-3.0F)))));
    public static final DeferredHolder<Item, Item> SCULK_BONE_PICKAXE = ITEMS.register("sculk_bone_pickaxe",
            () -> new PickaxeItem(Tiers.IRON, new Item.Properties().component(NTDataComponents.SCULK_EQUIPMENT, Boolean.TRUE)
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, (1.0F), (-2.8F)))));
    public static final DeferredHolder<Item, Item> SCULK_BONE_AXE = ITEMS.register("sculk_bone_axe",
            () -> new AxeItem(Tiers.IRON, new Item.Properties().component(NTDataComponents.SCULK_EQUIPMENT, Boolean.TRUE)
                    .attributes(AxeItem.createAttributes(Tiers.IRON, (6.0F), (-3.1F)))));
    public static final DeferredHolder<Item, Item> SCULK_BONE_HOE = ITEMS.register("sculk_bone_hoe",
            () -> new HoeItem(Tiers.IRON, new Item.Properties().component(NTDataComponents.SCULK_EQUIPMENT, Boolean.TRUE)
                    .attributes(HoeItem.createAttributes(Tiers.IRON, (-2.0F), (-1.0F)))));
    public static final DeferredHolder<Item, Item> WHALE_BONE_BOW = ITEMS.register("whale_bone_bow",
            () -> new BowItem(new Item.Properties().durability((500)).rarity(Rarity.UNCOMMON)));
    public static final DeferredHolder<Item, Item> KATANA = ITEMS.register("katana",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().durability(450)
                    .attributes(SwordItem.createAttributes(Tiers.IRON, (7.0F), (-2.4F)))));
    public static final DeferredHolder<Item, Item> DRAGONCAST_HELMET = ITEMS.register(
            "dragoncast_steel_helmet", () -> new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.HELMET.getDurability((37)))
                            .component(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE, Boolean.TRUE)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> DRAGONCAST_CHESTPLATE = ITEMS.register(
            "dragoncast_steel_chestplate", () -> new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.CHESTPLATE.getDurability((37)))
                            .component(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE, Boolean.TRUE)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> DRAGONCAST_LEGGINGS = ITEMS.register(
            "dragoncast_steel_leggings", () -> new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.LEGGINGS.getDurability((37)))
                            .component(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE, Boolean.TRUE)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    public static final DeferredHolder<Item, Item> DRAGONCAST_BOOTS = ITEMS.register(
            "dragoncast_steel_boots", () ->  new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.BOOTS.getDurability((37)))
                            .component(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE, Boolean.TRUE)
                            .component(NTDataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    // 缚相
    public static final DeferredHolder<Item, Item> H_BADLANDS = fx("h_badlands_metaphysica", List.of(Biomes.BADLANDS));
    public static final DeferredHolder<Item, Item> H_BASALT_DELTAS = fx("h_basalt_deltas_metaphysica", List.of(Biomes.BASALT_DELTAS));
    public static final DeferredHolder<Item, Item> H_BEACH = fx("h_beach_metaphysica", List.of(Biomes.BEACH));
    public static final DeferredHolder<Item, Item> H_BIRCH_FOREST = fx("h_birch_forest_metaphysica",
            List.of(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
    public static final DeferredHolder<Item, Item> H_CHERRY_GROVE = fx("h_cherry_grove_metaphysica", List.of(Biomes.CHERRY_GROVE));
    public static final DeferredHolder<Item, Item> H_CRIMSON_FOREST = fx("h_crimson_forest_metaphysica", List.of(Biomes.CRIMSON_FOREST));
    public static final DeferredHolder<Item, Item> H_DARK_FOREST = fx("h_dark_forest_metaphysica", List.of(Biomes.DARK_FOREST));
    public static final DeferredHolder<Item, Item> H_DEEPSLATE = fx("h_deepslate_metaphysica", List.of());
    public static final DeferredHolder<Item, Item> H_DEEP_DARK = fx("h_deep_dark_metaphysica", List.of(Biomes.DEEP_DARK));
    public static final DeferredHolder<Item, Item> H_DESERT = fx("h_desert_metaphysica", List.of(Biomes.DESERT));
    public static final DeferredHolder<Item, Item> H_DRIPSTONE_CAVES = fx("h_dripstone_caves_metaphysica", List.of(Biomes.DRIPSTONE_CAVES));
    public static final DeferredHolder<Item, Item> H_END = fx("h_end_metaphysica", List.of(Biomes.THE_END));
    public static final DeferredHolder<Item, Item> H_END_HIGHLANDS = fx("h_end_highlands_metaphysica",
            List.of(Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS));
    public static final DeferredHolder<Item, Item> H_FLOWER_FOREST = fx("h_flower_forest_metaphysica", List.of(Biomes.FLOWER_FOREST));
    public static final DeferredHolder<Item, Item> H_FOREST = fx("h_forest_metaphysica", List.of(Biomes.FOREST));
    public static final DeferredHolder<Item, Item> H_FROZEN_OCEAN = fx("h_frozen_ocean_metaphysica",
            List.of(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN));
    public static final DeferredHolder<Item, Item> H_OLD_GROWTH_TAIGA = fx("h_old_growth_taiga_metaphysica",
            List.of(Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA));
    public static final DeferredHolder<Item, Item> H_ICE_SPIKES = fx("h_ice_spikes_metaphysica", List.of(Biomes.ICE_SPIKES));
    public static final DeferredHolder<Item, Item> H_JUNGLE = fx("h_jungle_metaphysica",
            List.of(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE));
    public static final DeferredHolder<Item, Item> H_LUSH_CAVE = fx("h_lush_cave_metaphysica", List.of(Biomes.LUSH_CAVES));
    public static final DeferredHolder<Item, Item> H_MANGROVE_SWAMP = fx("h_mangrove_swamp_metaphysica", List.of(Biomes.MANGROVE_SWAMP));
    public static final DeferredHolder<Item, Item> H_MEADOW = fx("h_meadow_metaphysica", List.of(Biomes.MEADOW));
    public static final DeferredHolder<Item, Item> H_MOUNTAINS = fx("h_mountains_metaphysica", List.of(Biomes.STONY_PEAKS));
    public static final DeferredHolder<Item, Item> H_MUSHROOM = fx("h_mushroom_metaphysica", List.of(Biomes.MUSHROOM_FIELDS));
    public static final DeferredHolder<Item, Item> H_NETHER = fx("h_nether_metaphysica", List.of(Biomes.NETHER_WASTES));
    public static final DeferredHolder<Item, Item> H_OCEAN = fx("h_ocean_metaphysica",
            List.of(Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN));
    public static final DeferredHolder<Item, Item> H_PLAINS = fx("h_plains_metaphysica", List.of(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS));
    public static final DeferredHolder<Item, Item> H_RIVER = fx("h_river_metaphysica", List.of(Biomes.RIVER, Biomes.FROZEN_RIVER));
    public static final DeferredHolder<Item, Item> H_SAVANNA = fx("h_savanna_metaphysica", List.of(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU));
    public static final DeferredHolder<Item, Item> H_SNOWY_SLOPES = fx("h_snowy_slopes_metaphysica",
            List.of(Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS));
    public static final DeferredHolder<Item, Item> H_SNOWY_PLAINS = fx("h_snowy_plains_metaphysica", List.of(Biomes.SNOWY_PLAINS));
    public static final DeferredHolder<Item, Item> H_SOUL_SAND_VALLEY = fx("h_soul_sand_valley_metaphysica", List.of(Biomes.SOUL_SAND_VALLEY));
    public static final DeferredHolder<Item, Item> H_STONE_SHORE = fx("h_stone_shore_metaphysica", List.of(Biomes.STONY_SHORE));
    public static final DeferredHolder<Item, Item> H_SWAMP = fx("h_swamp_metaphysica", List.of(Biomes.SWAMP));
    public static final DeferredHolder<Item, Item> H_TAIGA = fx("h_taiga_metaphysica", List.of(Biomes.TAIGA, Biomes.SNOWY_TAIGA));
    public static final DeferredHolder<Item, Item> H_WARM_OCEAN = fx("h_warm_ocean_metaphysica", List.of(Biomes.WARM_OCEAN));
    public static final DeferredHolder<Item, Item> H_WARPED_FOREST = fx("h_warped_forest_metaphysica", List.of(Biomes.WARPED_FOREST));
    public static final DeferredHolder<Item, Item> H_WINDSWEPT = fx("h_windswept_metaphysica",
            List.of(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_SAVANNA, Biomes.WINDSWEPT_GRAVELLY_HILLS));
    public static final DeferredHolder<Item, Item> H_WOODED_BADLANDS = fx("h_wooded_badlands_metaphysica", List.of(Biomes.WOODED_BADLANDS));
    //刷怪蛋
    public static final DeferredHolder<Item, Item> LAVA_AXOLOTL_SPAWN_EGG = spawnEgg("lava_axolotl", NTEntityTypes.LAVA_AXOLOTL, 0x533a37, 0x7d2d00);
    public static final DeferredHolder<Item, Item> MOO_BLOOM_SPAWN_EGG = spawnEgg("moo_bloom", NTEntityTypes.MOO_BLOOM, 0xfaca00, 0xf7edc1);
    public static final DeferredHolder<Item, Item> DUCK_SPAWN_EGG = spawnEgg("duck", NTEntityTypes.DUCK, 0xa1a1a1, 0xe65626);

}