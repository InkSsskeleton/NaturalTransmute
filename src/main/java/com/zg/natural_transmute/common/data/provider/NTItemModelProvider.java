package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.state.NTBlockProperties;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NTItemModelProvider extends ItemModelProvider {

    public NTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(NTItems.REED.get());
        this.simpleItem(NTItems.PAPYRUS.get());
        this.simpleItem(NTItems.BLUEBERRIES.get());
        this.simpleItem(NTItems.WARPED_WART.get());
        this.bowItem(NTItems.WHALE_BONE_BOW.get());
        this.simpleBlockItem(NTBlocks.PLANTAIN_LEAVES.get());
        this.simpleBlockItem(NTBlocks.END_ALSOPHILA_LEAVES.get());
        this.simpleBlockItem(NTBlocks.END_ALSOPHILA_PLANKS.get());
        this.simpleBlockItemWithParent(NTBlocks.BUTTERCUP.get());
        this.withExistingParent(this.blockName(NTBlocks.PLANTAIN_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(NTBlocks.PLANTAIN_SAPLING.get())));
        this.withExistingParent(this.blockName(NTBlocks.END_ALSOPHILA_SAPLING.get()), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(NTBlocks.END_ALSOPHILA_SAPLING.get())));
        NTCommonUtils.getKnownBlockStream().filter(block -> block.properties() instanceof NTBlockProperties properties
                && properties.useSimpleBlockItem && !(block instanceof DoorBlock)).forEach(this::simpleBlockItem);
        NTCommonUtils.getKnownBlockStream().filter(block -> block instanceof DoorBlock).forEach(block -> this.basicItem(block.asItem()));
        NTCommonUtils.getKnownItemStream().filter(item -> item instanceof TieredItem).forEach(item ->
                this.withExistingParent(this.itemName(item), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + this.itemName(item))));
        NTCommonUtils.getKnownItemStream().filter(item -> item instanceof DeferredSpawnEggItem).forEach(item ->
                this.withExistingParent(this.itemName(item), this.mcLoc("item/template_spawn_egg")));
        NTCommonUtils.getKnownItemStream().filter(item -> item.components().has(NTDataComponents.SIMPLE_MODEL.get())).forEach(this::simpleItem);
    }

    private void simpleItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.withExistingParent(path, this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + path));
    }

    public ItemModelBuilder simpleBlockItem(Block block) {
        return this.withExistingParent(this.blockName(block), this.modLoc("block/" + this.blockName(block)));
    }

    private void bowItem(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        ModelFile.UncheckedModelFile bowModel = new ModelFile.UncheckedModelFile(this.modLoc("item/" + name));
        this.withExistingParent(name, this.modLoc("item/nt_bow"))
                .texture("layer0", this.modLoc("item/" + name))
                .override().predicate(this.modLoc("pulling"), 1)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.65F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.9F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_2"))).end();
        for (int i = 0; i < 3; i++) {
            String path = name + "_pulling_" + i;
            this.getBuilder(path).parent(bowModel).texture("layer0", this.modLoc("item/" + path));
        }
    }

    private void simpleBlockItemWithParent(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("block/" + this.blockName(block)));
    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}