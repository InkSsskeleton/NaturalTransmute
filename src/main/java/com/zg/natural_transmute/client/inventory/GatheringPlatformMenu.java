package com.zg.natural_transmute.client.inventory;

import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
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

public class GatheringPlatformMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public GatheringPlatformMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public GatheringPlatformMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(4), new SimpleContainerData(3));
    }

    public GatheringPlatformMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(NTMenus.GATHERING_PLATFORM.get(), containerId, inventory, access);
        checkContainerSize(inventory, 4);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 31, 23));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 131, 23));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 81, 5));
        this.addSlot(new NTResultSlot(itemHandler, 3, 81, 35));
        this.addDataSlots(containerData);
    }

    public float getGatheringTime() {
        float i = this.containerData.get(0);
        float j = this.containerData.get(1);
        return j != 0 && i != 0 ? Mth.clamp(i / j, 0.0F, 1.0F) : 0.0F;
    }

    public int getCurrentState() {
        return this.containerData.get(2);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copyOfSourceStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack sourceStack = slot.getItem();
            copyOfSourceStack = sourceStack.copy();
            if (index == 39) {
                if (!this.moveItemStackTo(sourceStack, 0, 35, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(sourceStack, copyOfSourceStack);
            } else if (index < 36) {
                if (sourceStack.is(NTItems.HETEROGENEOUS_STONE) || sourceStack.is(NTItemTags.METAPHYSICA)) {
                    if (!this.moveItemStackTo(sourceStack, 38, 39, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(sourceStack, 36, 38, Boolean.FALSE)) {
                    return ItemStack.EMPTY;
                } else if (index >= 0 && index < 27) {
                    if (!this.moveItemStackTo(sourceStack, 26, 35, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 27 && !this.moveItemStackTo(sourceStack, 0, 27, Boolean.FALSE)) {
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
        return stillValid(this.access, player, NTBlocks.GATHERING_PLATFORM.get());
    }

}