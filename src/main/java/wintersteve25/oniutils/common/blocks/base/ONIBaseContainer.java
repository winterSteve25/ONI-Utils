package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.init.ONIBlocks;

import java.util.List;

public abstract class ONIBaseContainer extends Container {

    protected ONIBaseTE tileEntity;
    protected PlayerEntity playerEntity;
    protected IItemHandler playerInventory;

    protected ONIBaseContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, ContainerType container) {
        super(container, windowId);

        tileEntity = (ONIBaseTE) (world.getTileEntity(pos));
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    protected void trackPower() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(h -> {
                    int energyStored = h.getPower() & 0xffff0000;
                    h.setPower(energyStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(h -> {
                    int energyStored = h.getPower() & 0x0000ffff;
                    h.setPower(energyStored | (value << 16));
                });
            }
        });
    }

    protected void trackProgress() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getProgress() & 0xffff;
            }

            @Override
            public void set(int value) {
                int progressStored = getProgress() & 0xffff0000;
                tileEntity.setProgress(progressStored + (value & 0xffff));
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getProgress() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int progressStored = getProgress() & 0x0000ffff;
                tileEntity.setProgress(progressStored | (value << 16));
            }
        });
    }

    protected void trackWorking() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getWorking() & 0xffff;
            }

            @Override
            public void set(int value) {
                int workingStored = getWorking() & 0xffff0000;
                int cache = workingStored + (value & 0xffff);

                tileEntity.setWorking(cache == 1);
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getWorking() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                int workingStored = getWorking() & 0x0000ffff;
                int cache = workingStored | (value << 16);

                tileEntity.setWorking(cache == 1);
            }
        });
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (validItems() != null) {
                    if (validItems().contains(stack.getItem())) {
                        if (!this.mergeItemStack(stack, 0, 1, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 28) {
                        if (!this.mergeItemStack(stack, 28, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (validItems() == null) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    } else if (index < 28) {
                        if (!this.mergeItemStack(stack, 28, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    public int getEnergy() {
        return tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).map(IPlasma::getPower).orElse(0);
    }

    public int getProgress() {
        return tileEntity.getProgress();
    }

    public int getWorking() {
        return tileEntity.getWorking() ? 1 : 0;
    }

    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    protected void addPlayerSlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity p_75145_1_) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ONIBlocks.COAL_GEN_BLOCK  );
    }

    public abstract List<Item> validItems();
}
