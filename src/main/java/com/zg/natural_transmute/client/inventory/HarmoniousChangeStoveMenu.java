package com.zg.natural_transmute.client.inventory;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTMenus;
import com.zg.natural_transmute.registry.NTTriggerTypes;
import com.zg.natural_transmute.utils.HarmoniousChangeFuelUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class HarmoniousChangeStoveMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(8), new SimpleContainerData(HarmoniousChangeStoveBlockEntity.Data.COUNT));
    }

    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(NTMenus.HARMONIOUS_CHANGE_STOVE.get(), containerId, inventory, access);
        checkContainerSize(inventory, 8);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 26, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 44, 15));
        this.addSlot(new FuelSlot(itemHandler, 3, 29, 45));
        this.addSlot(new BiomeCatalystSlot(itemHandler, 4, 68, 22));
        this.addSlot(new HCResultSlot(itemHandler, 5, 105, 41));
        this.addSlot(new HCResultSlot(itemHandler, 6, 123, 41));
        this.addSlot(new HCResultSlot(itemHandler, 7, 141, 41));
        this.addDataSlots(containerData);
    }

    public float getHarmoniousChangeTime() {
        float i = this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.TIME);
        float j = this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.TOTAL_TIME);
        return j != 0 && i != 0 ? Mth.clamp(i / j, 0.0F, 1.0F) : 0.0F;
    }

    public int getCurrentState() {
        return this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.CURRENT_STATE);
    }

    public int getFuelRemain() {
        return this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.FUEL_REMAIN);
    }

    public int getMaxFuelDuration() {
        return this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.MAX_FUEL_DURATION);
    }

    public boolean hasEternalFuel() {
        return this.containerData.get(HarmoniousChangeStoveBlockEntity.Data.HAS_ETERNAL_FUEL) == HarmoniousChangeStoveBlockEntity.Data.TRUE;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            result = slotItem.copy();
            if (index < 36) {
                if (HarmoniousChangeFuelUtils.isFuel(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, 39, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotItem.has(NTDataComponents.ASSOCIATED_BIOMES)) {
                    if (!this.moveItemStackTo(slotItem, 40, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(slotItem, 36, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.moveItemStackTo(slotItem, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotItem.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
    }

    private static class FuelSlot extends SlotItemHandler {

        public FuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return HarmoniousChangeFuelUtils.isFuel(stack);
        }

    }

    private static class BiomeCatalystSlot extends SlotItemHandler {

        public BiomeCatalystSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.has(NTDataComponents.ASSOCIATED_BIOMES);
        }

    }

    private static class HCResultSlot extends SlotItemHandler {

        public HCResultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            if (player instanceof ServerPlayer serverPlayer) {
                NTTriggerTypes.HARMONIOUS_CHANGE.get().trigger(serverPlayer, stack);
            }

            this.setChanged();
        }

    }

}