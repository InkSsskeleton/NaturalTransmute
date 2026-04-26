package com.zg.natural_transmute.common.blocks.entity;

import com.zg.natural_transmute.client.inventory.HarmoniousChangeStoveMenu;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipeInput;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import com.zg.natural_transmute.registry.NTRecipes;
import com.zg.natural_transmute.utils.HarmoniousChangeFuelUtils;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class HarmoniousChangeStoveBlockEntity extends SimpleContainerBlockEntity {
    public static final int INPUT_A_SLOT = 0;
    public static final int INPUT_B_SLOT = 1;
    public static final int INPUT_C_SLOT = 2;
    public static final int FUEL_SLOT = 3;
    public static final int BIOME_CATALYST_SLOT = 4;
    public static final int OUTPUT_A_SLOT = 5;
    public static final int OUTPUT_B_SLOT = 6;
    public static final int OUTPUT_C_SLOT = 7;

    public static final int IDLING_STATE = 0;
    public static final int WORKING_STATE = 1;

    private int time;
    private int totalTime;
    private int currentState;

    private int fuelRemain = 0;
    private final int maxFuelDuration = 8;    // Durin_Skeleton assumes max is always 8.
    private boolean hasEternalFuel = false;

    @Nullable
    public BlockPos mainPos;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<HarmoniousChangeRecipeInput, ? extends HarmoniousChangeRecipe> quickCheck;

    public HarmoniousChangeStoveBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(NTRecipes.HARMONIOUS_CHANGE_RECIPE.get());
        this.handler = new Handler(8);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HarmoniousChangeStoveBlockEntity blockEntity) {
        boolean shouldReset = false;
        boolean changed = false;

        if (blockEntity.hasInput()) {
            HarmoniousChangeRecipe recipe = blockEntity.checkHarmoniousChangeRecipe();
            if (recipe != null && blockEntity.canWork(recipe)) {    // Tick working
                if (!blockEntity.hasFuelRemain()) { // Consumes fuel
                    var fuel = blockEntity.getItem(FUEL_SLOT);
                    if (HarmoniousChangeFuelUtils.isFuel(fuel)) {
                        fuel.shrink(1);
                        blockEntity.fuelRemain = 8;
                        if (HarmoniousChangeFuelUtils.isEternalFuel(fuel)) {
                            blockEntity.hasEternalFuel = true;
                        }
                    } else {    // Fail to lit, reset
                        shouldReset = true;
                    }
                }

                var isRecipeDone = blockEntity.processRecipe(recipe);
                if (isRecipeDone) {
                    blockEntity.currentState = IDLING_STATE;
                    if (!blockEntity.hasEternalFuel) {
                        blockEntity.fuelRemain -= 1;
                    }
                } else {
                    blockEntity.currentState = WORKING_STATE;
                }
                changed = true;
            } else {
                shouldReset = true;
            }
        } else if (blockEntity.time > 0) {
            shouldReset = true;
        }

        if (shouldReset) {
            blockEntity.time = 0;
            // XXX: totalTime is not using here, fill it in processRecipe :(
            blockEntity.currentState = IDLING_STATE;
        }

        if (changed || shouldReset) {
            setChanged(level, pos, state);
        }
    }


    /**
     * Tick process recipe.
     *
     * @param recipe Recipe.
     * @return true for the recipe process was done, false for still processing.
     */
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
        ItemStack input1 = this.getItem(INPUT_A_SLOT);
        ItemStack input2 = this.getItem(INPUT_B_SLOT);
        ItemStack input3 = this.getItem(INPUT_C_SLOT);
        ItemStack biome_catalyst = this.getItem(BIOME_CATALYST_SLOT);

        List<ItemStack> ingredients = java.util.stream.Stream.of(input1, input2, input3)
                .filter(stack -> !stack.isEmpty())
                .collect(java.util.stream.Collectors.toList());

        return new HarmoniousChangeRecipeInput(ingredients, biome_catalyst);
    }

    private boolean hasFuelRemain() {
        return hasEternalFuel || fuelRemain > 0;
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
        this.fuelRemain = tag.getInt("FuelRemain");
        this.hasEternalFuel = tag.getBoolean("HasEternalFuel");
        this.currentState = tag.getInt("CurrentState");
        this.mainPos = NbtUtils.readBlockPos(tag, "MainPos").orElse(null);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Time", this.time);
        tag.putInt("TotalTime", this.totalTime);
        tag.putInt("FuelRemain", this.fuelRemain);
        tag.putBoolean("HasEternalFuel", this.hasEternalFuel);
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

    public class Data implements ContainerData {
        public static final int TIME = 0;
        public static final int TOTAL_TIME = 1;
        public static final int CURRENT_STATE = 2;
        public static final int FUEL_REMAIN = 3;
        public static final int MAX_FUEL_DURATION = 4;
        public static final int HAS_ETERNAL_FUEL = 5;

        public static final int TRUE = 1;
        public static final int FALSE = 0;

        public static final int COUNT = 6;

        @Override
        public int get(int index) {
            if (index == TIME) {
                return time;
            } else if (index == TOTAL_TIME) {
                return totalTime;
            } else if (index == CURRENT_STATE) {
                return currentState;
            } else if (index == FUEL_REMAIN) {
                return fuelRemain;
            } else if (index == MAX_FUEL_DURATION) {
                return maxFuelDuration;
            } else if (index == HAS_ETERNAL_FUEL) {
                return hasEternalFuel ? TRUE : FALSE;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == TIME) {
                time = value;
            } else if (index == TOTAL_TIME) {
                totalTime = value;
            } else if (index == CURRENT_STATE) {
                currentState = value;
            } else if (index == FUEL_REMAIN) {
                fuelRemain = Mth.clamp(value, 0, maxFuelDuration);
            } else if (index == MAX_FUEL_DURATION) {
                // No-op
            } else if (index == HAS_ETERNAL_FUEL) {
                hasEternalFuel = value != FALSE;
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}