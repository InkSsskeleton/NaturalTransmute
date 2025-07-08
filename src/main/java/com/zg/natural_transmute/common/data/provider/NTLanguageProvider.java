package com.zg.natural_transmute.common.data.provider;

import com.google.gson.JsonObject;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTEnchantments;
import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class NTLanguageProvider extends LanguageProvider {

    private final Map<String, String> enData = new TreeMap<>();
    private final Map<String, String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;

    public NTLanguageProvider(PackOutput output, String locale) {
        super(output, NaturalTransmute.MOD_ID, locale);
        this.output = output;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        this.addBlock(NTBlocks.TURQUOISE, "绿松石");
        this.addBlock(NTBlocks.BUTTERCUP, "毛茛");
        this.addBlock(NTBlocks.CORUNDUM, "刚玉");
        this.addBlock(NTBlocks.REED, "芦苇");
        this.addBlock(NTBlocks.PAPYRUS, "纸莎草");
        this.addBlock(NTBlocks.PITAYA, "火龙果");
        this.addBlock(NTBlocks.PLANTAIN, "芭蕉");
        this.addBlock(NTBlocks.PINEAPPLE, "凤梨");
        this.addBlock(NTBlocks.PEAT_MOSS, "泥炭藓");
        this.addBlock(NTBlocks.WARPED_WART, "诡异疣");
        this.addBlock(NTBlocks.AMBER_BLOCK, "琥珀块");
        this.addBlock(NTBlocks.AZURE_FROGLIGHT, "蔚蓝蛙明灯");
        this.addBlock(NTBlocks.ALGAL_END_STONE, "终末藻石");
        this.addBlock(NTBlocks.CAVE_EARTH, "洞窟丰壤");
        this.addBlock(NTBlocks.DEATH_EARTH, "死灵丰壤");
        this.addBlock(NTBlocks.GRASSLAND_EARTH, "草原丰壤");
        this.addBlock(NTBlocks.OCEAN_EARTH, "海洋丰壤");
        this.addBlock(NTBlocks.BLUE_TARO_VINE, "蓝芋藤");
        this.addBlock(NTBlocks.SIMULATED_RAMBLER, "拟蔷薇");
        this.addBlock(NTBlocks.PLANTAIN_STEM, "芭蕉茎");
        this.addBlock(NTBlocks.PLANTAIN_LEAVES, "芭蕉叶");
        this.addBlock(NTBlocks.PLANTAIN_SAPLING, "芭蕉树苗");
        this.addBlock(NTBlocks.GATHERING_PLATFORM, "聚相台");
        this.addBlock(NTBlocks.HARMONIOUS_CHANGE_STOVE, "谐变炉");
        this.addBlock(NTBlocks.BLUE_NETHER_BRICKS, "蓝色下界砖块");
        this.addBlock(NTBlocks.BLUE_NETHER_BRICK_SLAB, "蓝色下界砖块台阶");
        this.addBlock(NTBlocks.BLUE_NETHER_BRICK_STAIRS, "蓝色下界砖块楼梯");
        this.addBlock(NTBlocks.BLUE_NETHER_BRICK_WALL, "蓝色下界砖块墙");
        this.addBlock(NTBlocks.HETEROGENEOUS_STONE_ORE, "异相石矿石");
        this.addBlock(NTBlocks.DEEPSLATE_HETEROGENEOUS_STONE_ORE, "深层异相石矿石");
        this.addBlock(NTBlocks.ACTIVATED_TUBE_CORAL_BLOCK, "活性的管珊瑚块");
        this.addBlock(NTBlocks.ACTIVATED_BRAIN_CORAL_BLOCK, "活性的脑纹珊瑚块");
        this.addBlock(NTBlocks.ACTIVATED_BUBBLE_CORAL_BLOCK, "活性的气泡珊瑚块");
        this.addBlock(NTBlocks.ACTIVATED_FIRE_CORAL_BLOCK, "活性的火珊瑚块");
        this.addBlock(NTBlocks.ACTIVATED_HORN_CORAL_BLOCK, "活性的鹿角珊瑚块");
        this.addBlock(NTBlocks.ACTIVATED_TUBE_CORAL_FAN, "活性的管珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_BRAIN_CORAL_FAN, "活性的脑纹珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_BUBBLE_CORAL_FAN, "活性的气泡珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_FIRE_CORAL_FAN, "活性的火珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_HORN_CORAL_FAN, "活性的鹿角珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_TUBE_CORAL, "活性的管珊瑚");
        this.addBlock(NTBlocks.ACTIVATED_BRAIN_CORAL, "活性的脑纹珊瑚");
        this.addBlock(NTBlocks.ACTIVATED_BUBBLE_CORAL, "活性的气泡珊瑚");
        this.addBlock(NTBlocks.ACTIVATED_FIRE_CORAL, "活性的火珊瑚");
        this.addBlock(NTBlocks.ACTIVATED_HORN_CORAL, "活性的鹿角珊瑚");
        this.addBlock(NTBlocks.ACTIVATED_TUBE_CORAL_WALL_FAN, "墙上的活性的管珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_BRAIN_CORAL_WALL_FAN, "墙上的活性的脑纹珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_BUBBLE_CORAL_WALL_FAN, "墙上的活性的气泡珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_FIRE_CORAL_WALL_FAN, "墙上的活性的火珊瑚扇");
        this.addBlock(NTBlocks.ACTIVATED_HORN_CORAL_WALL_FAN, "墙上的活性的鹿角珊瑚扇");
        this.addBlock(NTBlocks.STRIPPED_END_ALSOPHILA_LOG, "去皮终末桫椤原木");
        this.addBlock(NTBlocks.STRIPPED_END_ALSOPHILA_WOOD, "去皮终末桫椤木");
        this.addBlock(NTBlocks.END_ALSOPHILA_LEAVES, "终末桫椤树叶");
        this.addBlock(NTBlocks.END_ALSOPHILA_PLANKS, "终末桫椤原木");
        this.addBlock(NTBlocks.END_ALSOPHILA_LOG, "终末桫椤原木");
        this.addBlock(NTBlocks.END_ALSOPHILA_WOOD, "终末桫椤木");
        this.addBlock(NTBlocks.END_ALSOPHILA_SAPLING, "终末桫椤树苗");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.getFirst(), "终末桫椤压力板");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(1), "终末桫椤栅栏门");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(2), "终末桫椤活板门");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(3), "终末桫椤按钮");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(4), "终末桫椤门");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(5), "终末桫椤楼梯");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.get(6), "终末桫椤栅栏");
        this.addBlock(NTBlocks.END_ALSOPHILA_FAMILY.getLast(), "终末桫椤台阶");
        this.addItem(NTItems.AMBER, "琥珀");
        this.addItem(NTItems.ANNIHILATE_ROD, "湮灭棒");
        this.addItem(NTItems.BERYL, "绿柱石");
        this.addItem(NTItems.BREEZE_ARROW, "风爆箭");
        this.addItem(NTItems.BLUEBERRIES, "蓝莓");
        this.addItem(NTItems.CHLORITE, "绿泥石");
        this.addItem(NTItems.COCONUT_SHELL, "椰子壳");
        this.addItem(NTItems.CORUNDUM_IRON_PLATE, "刚玉铁板");
        this.addItem(NTItems.DRAGONCAST_STEEL_BILLET, "龙铸钢坯");
        this.addItem(NTItems.DRAGONCAST_STEEL_INGOT, "龙铸钢锭");
        this.addItem(NTItems.DRAGONCAST_HELMET, "龙铸钢头盔");
        this.addItem(NTItems.DRAGONCAST_CHESTPLATE, "龙铸钢胸甲");
        this.addItem(NTItems.DRAGONCAST_LEGGINGS, "龙铸钢护腿");
        this.addItem(NTItems.DRAGONCAST_BOOTS, "龙铸钢靴子");
        this.addItem(NTItems.DRYAS_OCTOPETALA, "仙女木");
        this.addItem(NTItems.DUCK_SPAWN_EGG, "鸭刷怪蛋");
        this.addItem(NTItems.DUCK_EGG, "鸭蛋");
        this.addItem(NTItems.FANGS, "尖牙");
        this.addItem(NTItems.FLOWERING_BLUE_TARO_VINE, "开花的蓝芋藤");
        this.addItem(NTItems.GANODERMA_LUCIDUM, "灵芝");
        this.addItem(NTItems.GOOSE_BARNACLE, "鹅颈藤壶");
        this.addItem(NTItems.GREEN_GHOST_CRYSTAL, "绿幽灵水晶");
        this.addItem(NTItems.HAIR_CRYSTAL, "发晶");
        this.addItem(NTItems.HARMONIOUS_CHANGE_CORE, "谐变核心");
        this.addItem(NTItems.HARMONIOUS_CHANGE_FUEL, "谐变燃料");
        this.addItem(NTItems.HELIODOR, "金绿柱石");
        this.addItem(NTItems.HETEROGENEOUS_STONE, "异相石");
        this.addItem(NTItems.HONEY_WINE, "蜜酒");
        this.addItem(NTItems.ICELAND_SPAR, "冰洲石");
        this.addItem(NTItems.LAVA_AXOLOTL_BUCKET, "熔岩美西螈桶");
        this.addItem(NTItems.LAVA_AXOLOTL_SPAWN_EGG, "熔岩美西螈刷怪蛋");
        this.addItem(NTItems.MALACHITE, "孔雀石");
        this.addItem(NTItems.MELODIOUS_DISC, "悦动唱片");
        this.addItem(NTItems.MINE_WATER_BUCKET, "矿质水桶");
        this.addItem(NTItems.MOOSHROOM_STRANGE_FEED, "哞菇奇异饲料");
        this.addItem(NTItems.MOO_BLOOM_STRANGE_FEED, "哞花奇异饲料");
        this.addItem(NTItems.MOO_BLOOM_SPAWN_EGG, "哞花刷怪蛋");
        this.addItem(NTItems.PAPYRUS, "纸莎草");
        this.addItem(NTItems.PETARD, "炸药筒");
        this.addItem(NTItems.PITAYA, "火龙果");
        this.addItem(NTItems.REED, "芦苇");
        this.addItem(NTItems.RED_BERYL, "红绿柱石");
        this.addItem(NTItems.REFRIGERATED_ROCKET, "制冷火箭");
        this.addItem(NTItems.SCULK_BONE, "幽匿骨头");
        this.addItem(NTItems.SILVERFISH_PUPA, "蠹虫蛹");
        this.addItem(NTItems.TRANSPARENT_CRYSTAL, "透明水晶");
        this.addItem(NTItems.TRUFFLE, "松露");
        this.addItem(NTItems.WARPED_WART, "诡异疣");
        this.addItem(NTItems.WATER_WAX, "水蜡");
        this.addItem(NTItems.WHALE_BONE, "鲸骨");
        this.addItem(NTItems.WITHER_BONE, "凋灵骨");
        this.addItem(NTItems.BOTTLE_OF_TEA, "茶瓶");
        this.addItem(NTItems.COCONUT, "椰子");
        this.addItem(NTItems.TRUFFLE_SOUP, "松露羹");
        this.addItem(NTItems.PLANTAIN, "芭蕉");
        this.addItem(NTItems.PINEAPPLE, "凤梨");
        this.addItem(NTItems.DUCK, "生鸭肉");
        this.addItem(NTItems.COOKED_DUCK, "熟鸭肉");
        this.addItem(NTItems.VODKA, "伏特加");
        this.addItem(NTItems.HARMONIOUS_CHANGE_LAVA_BUCKET, "谐变熔浆桶");
        this.addItem(NTItems.ETERNAL_HARMONIOUS_CHANGE_LAVA_BUCKET, "永恒谐变熔浆桶");
        this.addItem(NTItems.CAT_FOOD_BRITISH_SHORTHAIR, "英国短毛猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_BLACK, "黑猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_CALICO, "花猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_JELLIE, "Jellie奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_PERSIAN, "波斯猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_RAGDOLL, "布偶猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_RED, "红虎斑猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_SIAMESE, "暹罗猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_TABBY, "虎斑猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_TUXEDO, "西服猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_WHITE, "白猫奇异猫粮");
        this.addItem(NTItems.CAT_FOOD_OCELOT, "豹猫奇异猫粮");
        this.addItem(NTItems.DOGFOOD_ASHEN, "灰狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_BLACK, "黑狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_CHESTNUT, "栗色狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_CLASSICS, "苍狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_RUSTY, "赭红狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_SNOWY, "雪狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_SPOTTED, "斑点狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_STRIPED, "条纹狼奇异狗粮");
        this.addItem(NTItems.DOGFOOD_WOODS, "森林狼奇异狗粮");
        this.addItem(NTItems.ANDESITE_SLAG, "安山岩熔渣");
        this.addItem(NTItems.BASALT_SLAG, "玄武岩熔渣");
        this.addItem(NTItems.DEEPSLATE_SLAG, "深板岩熔渣");
        this.addItem(NTItems.DIORITE_SLAG, "闪长岩熔渣");
        this.addItem(NTItems.GRANITE_SLAG, "花岗岩熔渣");
        this.addItem(NTItems.MUD_SLAG, "泥巴熔渣");
        this.addItem(NTItems.SANDSTONE_SLAG, "砂岩熔渣");
        this.addItem(NTItems.TUFF_SLAG, "凝灰岩熔渣");
        this.addItem(NTItems.SCULK_BONE_SWORD, "幽匿石剑");
        this.addItem(NTItems.SCULK_BONE_SHOVEL, "幽匿石锹");
        this.addItem(NTItems.SCULK_BONE_PICKAXE, "幽匿石镐");
        this.addItem(NTItems.SCULK_BONE_AXE, "幽匿石斧");
        this.addItem(NTItems.SCULK_BONE_HOE, "幽匿石锄");
        this.addItem(NTItems.WHALE_BONE_BOW, "鲸骨弓");
        this.addItem(NTItems.KATANA, "太刀");
        this.addItem(NTItems.H_BADLANDS, "枯槁缚相");
        this.addItem(NTItems.H_BASALT_DELTAS, "熔裂缚相");
        this.addItem(NTItems.H_BEACH, "涌潮缚相");
        this.addItem(NTItems.H_BIRCH_FOREST, "苍叶缚相");
        this.addItem(NTItems.H_CHERRY_GROVE, "繁樱缚相");
        this.addItem(NTItems.H_CRIMSON_FOREST, "赤菌缚相");
        this.addItem(NTItems.H_DARK_FOREST, "深林缚相");
        this.addItem(NTItems.H_DEEPSLATE, "深岩缚相");
        this.addItem(NTItems.H_DEEP_DARK, "回音缚相");
        this.addItem(NTItems.H_DESERT, "燥热缚相");
        this.addItem(NTItems.H_DRIPSTONE_CAVES, "岁月缚相");
        this.addItem(NTItems.H_END, "终末缚相");
        this.addItem(NTItems.H_END_HIGHLANDS, "龙诀缚相");
        this.addItem(NTItems.H_FLOWER_FOREST, "缤纷缚相");
        this.addItem(NTItems.H_FOREST, "树海缚相");
        this.addItem(NTItems.H_FROZEN_OCEAN, "冷翠缚相");
        this.addItem(NTItems.H_OLD_GROWTH_TAIGA, "巨人缚相");
        this.addItem(NTItems.H_ICE_SPIKES, "极寒缚相");
        this.addItem(NTItems.H_JUNGLE, "雨木缚相");
        this.addItem(NTItems.H_LUSH_CAVE, "秘园缚相");
        this.addItem(NTItems.H_MANGROVE_SWAMP, "蛙鸣缚相");
        this.addItem(NTItems.H_MEADOW, "和声缚相");
        this.addItem(NTItems.H_MOUNTAINS, "峦祈缚相");
        this.addItem(NTItems.H_MUSHROOM, "菌语缚相");
        this.addItem(NTItems.H_NETHER, "地狱缚相");
        this.addItem(NTItems.H_OCEAN, "浩瀚缚相");
        this.addItem(NTItems.H_PLAINS, "厚载缚相");
        this.addItem(NTItems.H_RIVER, "奔流缚相");
        this.addItem(NTItems.H_SAVANNA, "荒野缚相");
        this.addItem(NTItems.H_SNOWY_SLOPES, "凛峰缚相");
        this.addItem(NTItems.H_SNOWY_PLAINS, "霜洁缚相");
        this.addItem(NTItems.H_SOUL_SAND_VALLEY, "锁魂缚相");
        this.addItem(NTItems.H_STONE_SHORE, "沧碣缚相");
        this.addItem(NTItems.H_SWAMP, "淤潭缚相");
        this.addItem(NTItems.H_TAIGA, "冬林缚相");
        this.addItem(NTItems.H_WARM_OCEAN, "斑斓缚相");
        this.addItem(NTItems.H_WARPED_FOREST, "影染缚相");
        this.addItem(NTItems.H_WINDSWEPT, "浩风缚相");
        this.addItem(NTItems.H_WOODED_BADLANDS, "复苏缚相");
        this.addEnchantment(NTEnchantments.HEROIC, "英勇");
        this.addEntityType(NTEntityTypes.DUCK, "鸭");
        this.addEntityType(NTEntityTypes.LAVA_AXOLOTL, "熔岩美西螈");
        this.add("itemGroup." + NaturalTransmute.MOD_ID, "Natural Transmute", "自然通路");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        this.addTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(NaturalTransmute.MOD_ID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }

        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }

        return CompletableFuture.allOf();
    }

    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }

    private String getEnglishName(String path) {
        String[] words = path.split("_");
        for (int i = 0; i < words.length; i++) {
            String firstLetter = words[i].substring(0, 1);
            String remainingLetters = words[i].substring((1));
            words[i] = firstLetter.toUpperCase() + remainingLetters;
        }

        return String.join(" ", words);
    }

    private void addBlock(Supplier<? extends Block> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addBlock(DeferredHolder<Block, Block> key, String cn) {
        String path = BuiltInRegistries.BLOCK.getKey(key.get()).getPath();
        this.add(key.get().getDescriptionId(), this.getEnglishName(path), cn);
    }

    private void addItem(Supplier<? extends Item> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addItem(DeferredHolder<Item, Item> key, String cn) {
        String path = BuiltInRegistries.ITEM.getKey(key.get()).getPath();
        if (path.startsWith("h_")) {
            path = path.replaceFirst("h_", StringUtils.EMPTY);
        }

        this.add(key.get().getDescriptionId(), this.getEnglishName(path), cn);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String cn) {
        String path = BuiltInRegistries.ENTITY_TYPE.getKey(key.get()).getPath();
        this.add(key.get().getDescriptionId(), this.getEnglishName(path), cn);
    }

    public void addEnchantment(ResourceKey<Enchantment> key, String cn) {
        String en = this.getEnglishName(key.location().getPath());
        this.add("enchantment." + key.location().toLanguageKey(), en, cn);
    }

    private void addTooltips(String key, String en, String cn) {
        this.add("tooltip." + NaturalTransmute.MOD_ID + "." + key, en, cn);
    }

    private void add(String key, String en, String cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, cn);
        }
    }

}