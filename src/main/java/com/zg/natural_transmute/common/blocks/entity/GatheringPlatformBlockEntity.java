package com.zg.natural_transmute.common.blocks.entity;

import com.google.common.collect.Maps;
import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import com.zg.natural_transmute.common.components.AssociatedBiomes;
import com.zg.natural_transmute.common.event.NTEventFactory;
import com.zg.natural_transmute.common.items.crafting.GatheringRecipe;
import com.zg.natural_transmute.common.items.crafting.GatheringRecipeInput;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipes;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GatheringPlatformBlockEntity extends SimpleContainerBlockEntity {

    private int gatheringTime;
    private int totalGatheringTime;
    private int currentState;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<GatheringRecipeInput, ? extends GatheringRecipe> quickCheck;

    public GatheringPlatformBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.GATHERING_PLATFORM.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(NTRecipes.GATHERING_RECIPE.get());
        this.handler = new Handler(4);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GatheringPlatformBlockEntity blockEntity) {
        ItemStack coreIngredient = blockEntity.handler.getStackInSlot(2);
        GatheringRecipe recipe = blockEntity.checkGatheringRecipe();
        if (recipe != null) {
            blockEntity.gathering(recipe, pos, state);
        } else {
            blockEntity.totalGatheringTime = 0;
            blockEntity.gatheringTime = 0;
            setChanged(level, pos, state);
        }

        if (coreIngredient.has(NTDataComponents.ASSOCIATED_BIOMES)) {
            blockEntity.currentState = 2;
            setChanged(level, pos, state);
        } else if (coreIngredient.is(NTItems.HETEROGENEOUS_STONE)) {
            blockEntity.currentState = 1;
            setChanged(level, pos, state);
        } else {
            blockEntity.currentState = 0;
            setChanged(level, pos, state);
        }
    }

    private static Map<Item, Boolean> getSpecialFuXiangs(GatheringPlatformBlockEntity blockEntity) {
        Map<Item, Boolean> map = Maps.newHashMap();
        BlockPos pos = blockEntity.getBlockPos();
        Level level = blockEntity.getLevel();
        if (level != null) {
            ResourceKey<Level> dimension = level.dimension();
            map.put(NTItems.H_DEEPSLATE.get(), pos.getY() < 0 && dimension == Level.OVERWORLD);
            NTEventFactory.onRegisterSpecialFuXiangCraftingCondition(map, blockEntity);
        }

        return map;
    }

    private void gathering(GatheringRecipe recipe, BlockPos pos, BlockState state) {
        if (this.level != null) {
            AssociatedBiomes biomes = recipe.result.get(NTDataComponents.ASSOCIATED_BIOMES);
            Boolean flagObj = getSpecialFuXiangs(this).get(recipe.result.getItem());
            boolean flag = flagObj != null && flagObj;

            boolean canCraft = biomes == null ||
                    biomes.biomes().contains(this.level.getBiome(pos).getKey()) ||
                    flag;

            if (canCraft) {
                ItemStack assemble = recipe.assemble(this.getRecipeInput(), this.level.registryAccess());
                this.totalGatheringTime = this.gatheringTime;
                this.gatheringTime++;
                if (this.gatheringTime > recipe.gatheringTime) {
                    this.handler.insertItem(3, assemble, false);
                    NTCommonUtils.consumeIngredients(this);
                    this.totalGatheringTime = 0;
                    this.gatheringTime = 0;
                }
            }

            setChanged(this.level, pos, state);
        }
    }

    @Nullable
    private GatheringRecipe checkGatheringRecipe() {
        if (this.level != null) {
            RecipeHolder<? extends GatheringRecipe> holder = this.quickCheck.getRecipeFor(this.getRecipeInput(), this.level).orElse(null);
            return holder != null ? holder.value() : null;
        }

        return null;
    }

    private GatheringRecipeInput getRecipeInput() {
        ItemStack input1 = this.getItem(0);
        ItemStack input2 = this.getItem(1);
        ItemStack core = this.getItem(2);
        return new GatheringRecipeInput(input1, input2, core);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.gatheringTime = tag.getInt("GatheringTime");
        this.totalGatheringTime = tag.getInt("TotalGatheringTime");
        this.currentState = tag.getInt("CurrentState");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("GatheringTime", this.gatheringTime);
        tag.putInt("TotalGatheringTime", this.totalGatheringTime);
        tag.putInt("CurrentState", this.currentState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(this.level, this.worldPosition);
        return new GatheringPlatformMenu(containerId, inventory, access, this.handler, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return gatheringTime;
            } else if (index == 1) {
                return totalGatheringTime;
            } else if (index == 2) {
                return currentState;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                gatheringTime = value;
            } else if (index == 1) {
                totalGatheringTime = value;
            } else if (index == 2) {
                currentState = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}