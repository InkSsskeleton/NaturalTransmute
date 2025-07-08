package com.zg.natural_transmute.common.data;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTBiomeModifiers;
import com.zg.natural_transmute.registry.NTConfiguredFeatures;
import com.zg.natural_transmute.registry.NTEnchantments;
import com.zg.natural_transmute.registry.NTPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, NTEnchantments::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, NTConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, NTPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NTBiomeModifiers::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NaturalTransmute.MOD_ID));
    }

}