package com.zg.natural_transmute.client.inventory;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTMenus;
import com.zg.natural_transmute.registry.NTTriggerTypes;
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
        this(containerId, inventory, access, new ItemStackHandler(8), new SimpleContainerData(5));
    }

    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(NTMenus.HARMONIOUS_CHANGE_STOVE.get(), containerId, inventory, access);
        checkContainerSize(inventory, 8);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 26, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 44, 15));
        this.addSlot(new FuelSlot(itemHandler, 3, 29, 45));
        this.addSlot(new FuXiangSlot(itemHandler, 4, 68, 22));
        this.addSlot(new HCResultSlot(itemHandler, 5, 105, 41));
        this.addSlot(new HCResultSlot(itemHandler, 6, 123, 41));
        this.addSlot(new HCResultSlot(itemHandler, 7, 141, 41));
        this.addDataSlots(containerData);
    }

    public float getHarmoniousChangeTime() {
        float i = this.containerData.get(0);
        float j = this.containerData.get(1);
        return j != 0 && i != 0 ? Mth.clamp(i / j, 0.0F, 1.0F) : 0.0F;
    }

    public int getCurrentState() {
        return this.containerData.get(2);
    }

    public float getRemaining() {
        float i = this.containerData.get(3);
        int j = this.containerData.get(4);
        if (j == 0) {
            j = 320;
        }

        return Mth.clamp(i / (float)j, 0.0F, 1.0F);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copyOfSourceStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack sourceStack = slot.getItem();
            copyOfSourceStack = sourceStack.copy();
            if (index >= 41 && index <= 43) {
                if (!this.moveItemStackTo(sourceStack, 0, 35, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(sourceStack, copyOfSourceStack);
            } else if (index < 41) {
                if (HarmoniousChangeStoveBlockEntity.getFuel().containsKey(sourceStack.getItem())) {
                    if (!this.moveItemStackTo(sourceStack, 39, 40, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (sourceStack.is(NTItemTags.METAPHYSICA)) {
                    if (!this.moveItemStackTo(sourceStack, 40, 41, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(sourceStack, 36, 38, Boolean.FALSE)) {
                    return ItemStack.EMPTY;
                }  else if (index >= 0 && index < 27) {
                    if (!this.moveItemStackTo(sourceStack, 26, 35, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 27 && index < 36 && !this.moveItemStackTo(sourceStack, 0, 27, Boolean.FALSE)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(sourceStack, 0, 35, Boolean.FALSE)) {
                return ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (sourceStack.getCount() == copyOfSourceStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, sourceStack);
        }

        return copyOfSourceStack;
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
            return HarmoniousChangeStoveBlockEntity.getFuel().containsKey(stack.getItem());
        }

    }

    private static class FuXiangSlot extends SlotItemHandler {

        public FuXiangSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
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