package com.zg.natural_transmute.common.data.advancements;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.data.advancements.critereon.HarmoniousChangeTrigger;
import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class NTAdvancements implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder.advancement().display(NTItems.HETEROGENEOUS_STONE.get(),
                        text("heterogeneous_stone", "title"),
                        text("get_heterogeneous_stone", "description"),
                        NaturalTransmute.prefix("textures/block/turquoise.png"),
                        AdvancementType.TASK, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)
                .addCriterion("heterogeneous_stone", InventoryChangeTrigger.TriggerInstance
                        .hasItems(NTItems.HETEROGENEOUS_STONE.get()))
                .save(writer, NaturalTransmute.prefix("heterogeneous_stone").toString());
        Advancement.Builder.advancement().display(NTBlocks.GATHERING_PLATFORM.get(),
                        text("fu_xiang", "title"),
                        text("fu_xiang", "description"),
                        (null), AdvancementType.TASK, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)
                .addCriterion("fu_xiang", InventoryChangeTrigger.TriggerInstance
                        .hasItems(ItemPredicate.Builder.item().of(NTItemTags.BIOME_CATALYST)))
                .save(writer, NaturalTransmute.prefix("fu_xiang").toString());
        Advancement.Builder.advancement().display(NTItems.HARMONIOUS_CHANGE_CORE.get(),
                        text("harmonious_change", "title"),
                        text("harmonious_change", "description"),
                        (null), AdvancementType.TASK, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)
                .addCriterion("harmonious_change", HarmoniousChangeTrigger.TriggerInstance.harmoniousChange())
                .save(writer, NaturalTransmute.prefix("harmonious_change").toString());
    }

    private static Component text(String name, String suffix) {
        return Component.translatable("advancements." + NaturalTransmute.MOD_ID + "." + name + "." + suffix);
    }

}