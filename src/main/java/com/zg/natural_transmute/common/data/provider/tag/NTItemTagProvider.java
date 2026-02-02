package com.zg.natural_transmute.common.data.provider.tag;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.data.tags.NTBlockTags;
import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NTItemTagProvider extends ItemTagsProvider {

    public NTItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.copy(NTBlockTags.END_ALSOPHILA_LOGS, NTItemTags.END_ALSOPHILA_LOGS);
        this.copy(NTBlockTags.END_ALSOPHILA_SAPLING_PLACEABLE,
                NTItemTags.END_ALSOPHILA_SAPLING_PLACEABLE);
        this.tag(Tags.Items.TOOLS_BOW).add(NTItems.WHALE_BONE_BOW.get());
        this.tag(Tags.Items.FOODS_COOKED_MEAT).add(NTItems.COOKED_DUCK.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(NTItems.WHALE_BONE_BOW.get());
        this.tag(ItemTags.BOW_ENCHANTABLE).add(NTItems.WHALE_BONE_BOW.get());
        this.tag(ItemTags.ARROWS).add(NTItems.BREEZE_ARROW.get());
        this.tag(NTItemTags.GRASS).add(Items.SHORT_GRASS,
                Items.TALL_GRASS, Items.FERN, Items.LARGE_FERN);
        this.tag(NTItemTags.FRUIT).add(Items.APPLE, Items.MELON_SLICE, Items.SUGAR_CANE,
                Items.CHORUS_FRUIT, Items.SWEET_BERRIES, Items.GLOW_BERRIES);
        this.tag(NTItemTags.VEGETABLE).add(Items.CARROT, Items.POTATO, Items.POISONOUS_POTATO,
                Items.BEETROOT, Items.PUMPKIN, Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM);
        NTCommonUtils.getKnownItems().forEach(item -> {
            ItemStack stack = item.getDefaultInstance();
            if (stack.has(NTDataComponents.ASSOCIATED_BIOMES)) {
                this.tag(NTItemTags.BIOME_CATALYST).add(item);
            }

            if (stack.has(NTDataComponents.CAT_FOODS)) {
                this.tag(ItemTags.CAT_FOOD).add(item);
            }

            if (stack.has(NTDataComponents.DOG_FOODS)) {
                this.tag(ItemTags.WOLF_FOOD).add(item);
            }
        });
    }

}