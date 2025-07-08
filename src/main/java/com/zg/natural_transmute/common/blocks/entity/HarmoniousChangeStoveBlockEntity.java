package com.zg.natural_transmute.common.blocks.entity;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.zg.natural_transmute.client.inventory.HarmoniousChangeStoveMenu;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipes;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

public class HarmoniousChangeStoveBlockEntity extends SimpleContainerBlockEntity {

    private int time;
    private int totalTime;
    private int currentState;
    private int litTime;
    private int litDuration;
    @Nullable
    public BlockPos mainPos;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<HarmoniousChangeRecipeInput, ? extends HarmoniousChangeRecipe> quickCheck;
    @Nullable
    private static volatile Map<Item, Integer> fuelCache;

    public HarmoniousChangeStoveBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(NTRecipes.HARMONIOUS_CHANGE_RECIPE.get());
        this.handler = new Handler(8);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HarmoniousChangeStoveBlockEntity blockEntity) {
        boolean didInventoryChange = false;
        if (blockEntity.hasInput()) {
            HarmoniousChangeRecipe recipe = blockEntity.checkHarmoniousChangeRecipe();
            if (recipe != null && blockEntity.canWork(recipe)) {
                if (blockEntity.isLit()) {
                    didInventoryChange = blockEntity.processRecipe(recipe);
                    blockEntity.currentState = 1;
                } else {
                    blockEntity.litTime = getFuel().get(blockEntity.getItem(3).getItem());
                    blockEntity.litDuration = blockEntity.litTime;
                }
            } else {
                blockEntity.time = 0;
                blockEntity.currentState = 0;
            }
        } else if (blockEntity.time > 0) {
            blockEntity.time = 0;
        }

        if (didInventoryChange) {
            if (blockEntity.litTime > 0) {
                blockEntity.litTime--;
            }

            setChanged(level, pos, state);
        }
    }

    private boolean processRecipe(HarmoniousChangeRecipe recipe) {
        if (this.level == null) {
            return false;
        }

        ++this.time;
        this.totalTime = recipe.getTime();
        if (this.time < this.totalTime) {
            return false;
        }

        this.time = 0;
        ItemStack resultStack = recipe.assemble(this.getRecipeInput(), this.level.registryAccess());
        ItemStack outStack = this.handler.getStackInSlot(5);
        ItemStack extraStack = this.handler.getStackInSlot(6);
        ItemStack resultExtraStack = recipe.getResults().size() > 1 ?
                recipe.getResults().get(1) : ItemStack.EMPTY;
        if (outStack.isEmpty()) {
            this.handler.setStackInSlot(5, resultStack.copy());
        } else if (ItemStack.isSameItemSameComponents(outStack, resultStack)) {
            outStack.grow(resultStack.getCount());
        }

        if (!resultExtraStack.isEmpty()) {
            if (extraStack.isEmpty()) {
                this.handler.setStackInSlot(6, resultExtraStack.copy());
            } else if (ItemStack.isSameItemSameComponents(extraStack, resultExtraStack)) {
                extraStack.grow(resultExtraStack.getCount());
            }
        }

        if (recipe.isConsume()) {
            NTCommonUtils.consumeIngredients(this);
        }

        return true;
    }

    @Nullable
    private HarmoniousChangeRecipe checkHarmoniousChangeRecipe() {
        if (this.level != null) {
            RecipeHolder<? extends HarmoniousChangeRecipe> holder = this.quickCheck.getRecipeFor(this.getRecipeInput(), this.level).orElse(null);
            return holder != null ? holder.value() : null;
        }

        return null;
    }

    private HarmoniousChangeRecipeInput getRecipeInput() {
        ItemStack input1 = this.getItem(0);
        ItemStack input2 = this.getItem(1);
        ItemStack input3 = this.getItem(2);
        ItemStack fuel = this.getItem(3);
        ItemStack metaphysica = this.getItem(4);
        List<ItemStack> ingredients = Arrays.asList(input1, input2, input3);
        ingredients.removeIf(ItemStack::isEmpty);
        return new HarmoniousChangeRecipeInput(ingredients, fuel, metaphysica);
    }

    private boolean isLit() {
        ItemStack fuel = this.getItem(3);
        boolean b1 = fuel.is(NTItems.HARMONIOUS_CHANGE_LAVA_BUCKET);
        boolean b2 = fuel.is(NTItems.ETERNAL_HARMONIOUS_CHANGE_LAVA_BUCKET);
        return this.litTime > 0 || b1 || b2;
    }

    private boolean hasInput() {
        for (int i = 0; i < 4; ++i) {
            if (!this.handler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    protected boolean canWork(HarmoniousChangeRecipe recipe) {
        if (this.level != null && recipe.matches(this.getRecipeInput(), this.level)) {
            boolean checkExtra;
            ItemStack resultStack = recipe.getResults().getFirst();
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outStack = this.handler.getStackInSlot(5);
                if (outStack.isEmpty()) {
                    checkExtra = true;
                } else if (!ItemStack.isSameItemSameComponents(outStack, resultStack)) {
                    return false;
                } else {
                    checkExtra = outStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }

                ItemStack resultExtraStack1 = recipe.getResults().size() > 1 ? recipe.getResults().get(1) : ItemStack.EMPTY;
                if (resultExtraStack1.isEmpty()) {
                    return checkExtra;
                } else {
                    ItemStack extraStack1 = this.handler.getStackInSlot(6);
                    if (extraStack1.isEmpty()) {
                        checkExtra = true;
                    } else if (!ItemStack.isSameItemSameComponents(extraStack1, resultExtraStack1)) {
                        return false;
                    } else {
                        checkExtra = extraStack1.getCount() + resultExtraStack1.getCount() <= resultExtraStack1.getMaxStackSize();
                    }

                    ItemStack resultExtraStack2 = recipe.getResults().size() > 2 ? recipe.getResults().get(2) : ItemStack.EMPTY;
                    if (resultExtraStack2.isEmpty()) {
                        return checkExtra;
                    } else if (checkExtra) {
                        ItemStack extraStack2 = this.handler.getStackInSlot(7);
                        if (extraStack2.isEmpty()) {
                            return true;
                        } else if (!ItemStack.isSameItemSameComponents(extraStack2, resultExtraStack2)) {
                            return false;
                        } else {
                            return extraStack2.getCount() + resultExtraStack2.getCount() <= resultExtraStack2.getMaxStackSize();
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.time = tag.getInt("Time");
        this.totalTime = tag.getInt("TotalTime");
        this.litTime = tag.getInt("LitTime");
        this.litDuration = tag.getInt("LitDuration");
        this.currentState = tag.getInt("CurrentState");
        this.mainPos = NbtUtils.readBlockPos(tag, "MainPos").orElse(null);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Time", this.time);
        tag.putInt("TotalTime", this.totalTime);
        tag.putInt("LitTime", this.litTime);
        tag.putInt("LitDuration", this.litDuration);
        tag.putInt("CurrentState", this.currentState);
        if (this.mainPos != null) {
            tag.put("MainPos", NbtUtils.writeBlockPos(this.mainPos));
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(Objects.requireNonNull(this.level), this.worldPosition);
        return new HarmoniousChangeStoveMenu(containerId, inventory, access, this.handler, this.containerData);
    }

    public static void invalidateCache() {
        fuelCache = null;
    }

    public static Map<Item, Integer> getFuel() {
        Map<Item, Integer> cacheMap = fuelCache;
        if (cacheMap != null) {
            return cacheMap;
        } else {
            Map<Item, Integer> map = Maps.newLinkedHashMap();
            buildFuels((e, amount) -> e.ifRight(tag -> add(map, tag, amount))
                    .ifLeft(item -> map.put(item.asItem(), amount)));
            fuelCache = map;
            return map;
        }
    }

    private static void add(ObjIntConsumer<Either<Item, TagKey<Item>>> consumer, ItemLike item, int amount) {
        consumer.accept(Either.left(item.asItem()), amount);
    }

    private static void add(ObjIntConsumer<Either<Item, TagKey<Item>>> consumer, TagKey<Item> tag, int amount) {
        consumer.accept(Either.right(tag), amount);
    }

    public static void buildFuels(ObjIntConsumer<Either<Item, TagKey<Item>>> map) {
        add(map, Items.AMETHYST_SHARD, 2);
        add(map, Items.LAPIS_LAZULI, 4);
        add(map, NTItems.HARMONIOUS_CHANGE_FUEL.get(), 8);
    }

    private static void add(Map<Item, Integer> map, TagKey<Item> itemTag, int amount) {
        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTag)) {
            map.put(holder.value(), amount);
        }
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return time;
            } else if (index == 1) {
                return totalTime;
            } else if (index == 2) {
                return currentState;
            } else if (index == 3) {
                if (litDuration > Short.MAX_VALUE) {
                    return Mth.floor(((double) litTime / litDuration) * Short.MAX_VALUE);
                }

                return litTime;
            } else if (index == 4) {
                return Math.min(litDuration, Short.MAX_VALUE);
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                time = value;
            } else if (index == 1) {
                totalTime = value;
            } else if (index == 2) {
                currentState = value;
            } else if (index == 3) {
                litTime = value;
            } else if (index == 4) {
                litDuration = value;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

}