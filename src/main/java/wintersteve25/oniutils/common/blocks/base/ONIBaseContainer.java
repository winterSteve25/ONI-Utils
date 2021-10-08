package wintersteve25.oniutils.common.blocks.base;

import mekanism.api.chemical.gas.GasStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import wintersteve25.oniutils.common.blocks.base.interfaces.*;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.items.modules.modifications.ONIBaseModification;

import javax.annotation.Nonnull;
import java.util.HashMap;

public abstract class ONIBaseContainer extends Container {

    protected ONIBaseTE tileEntity;
    protected PlayerEntity playerEntity;
    protected IItemHandler playerInventory;
    private boolean isModTabOpen = false;

    protected ONIBaseContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, ContainerType container) {
        super(container, windowId);

        tileEntity = (ONIBaseTE) (world.getTileEntity(pos));
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            int startPlayerInvIndex = getInvSize() + getModSlotAmount();
            int startPlayerHBIndex = getInvSize() + getModSlotAmount() + 27;
            int endPlayerInvIndex = inventorySlots.size();
            int startMachineIndex = 0;
            int startModSlotIndex = getInvSize();

            if (slot instanceof ONIMachineSlotHandler || slot instanceof ONIModSlotHandler) {
                if (!this.mergeItemStack(stack, startPlayerInvIndex, endPlayerInvIndex, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (itemstack.getItem() instanceof ONIBaseModification) {
                    if (!this.mergeItemStack(stack, startModSlotIndex, startPlayerInvIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onSlotChange(stack, itemstack);
                }
                if (index >= startPlayerHBIndex) {
                    if (!this.mergeItemStack(stack, startMachineIndex, startPlayerHBIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (index >= startPlayerInvIndex && index < endPlayerInvIndex) {
                    if (!this.mergeItemStack(stack, startMachineIndex, startPlayerInvIndex, false)) {
                        if (!this.mergeItemStack(stack, startPlayerHBIndex, endPlayerInvIndex, false)) {
                            return ItemStack.EMPTY;
                        }
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

    @Override
    public boolean canInteractWith(PlayerEntity p_75145_1_) {
        if (tileEntity == null) {
            return false;
        }

        if (tileEntity.getWorld() == null) {
            return false;
        }

        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ONIBlocks.COAL_GEN_BLOCK);
    }

    protected void trackPower() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getPower() & 0xffff;
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
                return (getPower() >> 16) & 0xffff;
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
        if (tileEntity instanceof ONIIHasProgress) {
            ONIIHasProgress hasProgress = (ONIIHasProgress) tileEntity;
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return getProgress() & 0xffff;
                }

                @Override
                public void set(int value) {
                    int progressStored = getProgress() & 0xffff0000;
                    hasProgress.setProgress(progressStored + (value & 0xffff));
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
                    hasProgress.setProgress(progressStored | (value << 16));
                }
            });
        } else {
            throw new UnsupportedOperationException("Trying to track progress on a machine that does not support progress!");
        }
    }

    protected void trackTotalProgress() {
        if (tileEntity instanceof ONIIHasProgress) {
            ONIIHasProgress hasProgress = (ONIIHasProgress) tileEntity;
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return getTotalProgress() & 0xffff;
                }

                @Override
                public void set(int value) {
                    int progressStored = getTotalProgress() & 0xffff0000;
                    hasProgress.setTotalProgress(progressStored + (value & 0xffff));
                }
            });
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return (getTotalProgress() >> 16) & 0xffff;
                }

                @Override
                public void set(int value) {
                    int progressStored = getTotalProgress() & 0x0000ffff;
                    hasProgress.setTotalProgress(progressStored | (value << 16));
                }
            });
        } else {
            throw new UnsupportedOperationException("Trying to track total progress on a machine that does not support progress!");
        }
    }

    protected void trackWorking() {
        if (tileEntity instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) tileEntity;
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return getWorking() & 0xffff;
                }

                @Override
                public void set(int value) {
                    int workingStored = getWorking() & 0xffff0000;
                    int cache = workingStored + (value & 0xffff);

                    workable.setWorking(cache == 1);
                }
            });
        } else {
            throw new UnsupportedOperationException("Trying to track working on a machine that does not support progress!");
        }
    }

    public void trackRedstoneInverted() {
        if (tileEntity instanceof ONIIForceStoppable) {
            ONIIForceStoppable forceStoppable = (ONIIForceStoppable) tileEntity;
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return getForceStopped() & 0xffff;
                }

                @Override
                public void set(int value) {
                    int stored = getForceStopped() & 0xffff0000;
                    int cache = stored + (value & 0xffff);

                    forceStoppable.setForceStopped(cache == 1);
                }
            });
        } else {
            throw new UnsupportedOperationException("Trying to track working on a machine that does not support progress!");
        }
    }

    protected void trackPowerCapacity() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getPowerCapacity() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(h -> {
                    int capacityStored = h.getCapacity() & 0xffff0000;
                    h.setCapacity(capacityStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getPowerCapacity() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(h -> {
                    int capacityStored = h.getPower() & 0x0000ffff;
                    h.setCapacity(capacityStored | (value << 16));
                });
            }
        });
    }

    public int getPower() {
        if (tileEntity == null) {
            return 0;
        }
        return tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).map(IPlasma::getPower).orElse(0);
    }

    public int getProgress() {
        if (tileEntity instanceof ONIIHasProgress) {
            ONIIHasProgress hasProgress = (ONIIHasProgress) tileEntity;
            return hasProgress.getProgress();
        }
        throw new UnsupportedOperationException("trying to get progress on an tile that does not support progress");
    }

    public int getTotalProgress() {
        if (tileEntity instanceof ONIIHasProgress) {
            ONIIHasProgress hasProgress = (ONIIHasProgress) tileEntity;
            return hasProgress.getTotalProgress();
        }
        throw new UnsupportedOperationException("trying to get total progress on an tile that does not support progress");
    }

    public byte getWorking() {
        if (tileEntity instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) tileEntity;
            return (byte) (workable.getWorking() ? 1 : 0);
        }
        throw new UnsupportedOperationException("trying to get working on an tile that does not support progress");
    }

    public byte getForceStopped() {
        if (tileEntity instanceof ONIIForceStoppable) {
            ONIIForceStoppable forceStoppable = (ONIIForceStoppable) tileEntity;
            return (byte) (forceStoppable.getForceStopped() ? 1 : 0);
        }
        throw new UnsupportedOperationException("trying to get working on an tile that does not support progress");
    }

    public int getPowerCapacity() {
        if (tileEntity == null) {
            return 0;
        }
        return tileEntity.getCapability(PlasmaCapability.POWER_CAPABILITY).map(IPlasma::getCapacity).orElse(0);
    }

    public ONIBaseTE getTileEntity() {
        return tileEntity;
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

    public HashMap<Item, Integer> validItems() {
        if (tileEntity instanceof ONIIHasValidItems) {
            ONIIHasValidItems hasValidItems = (ONIIHasValidItems) tileEntity;
            return hasValidItems.validItems();
        }
        return null;
    }

    public void setModTabOpen(boolean in) {
        this.isModTabOpen = in;
    }

    public boolean isModTabOpen() {
        return isModTabOpen;
    }

    public int getModSlotAmount() {
        if (getTileEntity() instanceof ONIIModifiable) {
            ONIIModifiable modifiable = (ONIIModifiable) getTileEntity();
            if (modifiable == null || modifiable.modContext() == null) return 0;
            return modifiable.modContext().getMaxModAmount();
        }
        return 0;
    }

    public int getInvSize() {
        if (getTileEntity() instanceof ONIBaseInvTE) {
            ONIBaseInvTE invTE = (ONIBaseInvTE) getTileEntity();
            return invTE.getInvSize();
        }
        return 0;
    }

    protected void addModificationSlots(IItemHandler itemHandler) {
        int slotFixX = 0;
        int slotFixY = 0;
        int index = 0;

        for (int a = 0; a < getModSlotAmount(); a++) {
            if (slotFixX > 100) {
                slotFixX = 0;
                slotFixY += 20;
            }
            addSlot(new ONIModSlotHandler(itemHandler, index, -136 + slotFixX, 26 + slotFixY));
            slotFixX += 20;
            index++;
        }
    }

    protected void addMachineSlot(IItemHandler itemHandler, int index, Tuple<Integer, Integer> tuple) {
        addSlot(new ONIMachineSlotHandler(itemHandler, index, tuple.getA(), tuple.getB()));
    }

    public boolean hasPower() {
        return isPowerConsumer() || isPowerProducer();
    }

    public boolean isPowerProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.POWER_PRODUCER);
        }
        return false;
    }

    public boolean isPowerConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.POWER_CONSUMER);
        }
        return false;
    }

    public boolean isGasProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.GAS_PRODUCER);
        }
        return false;
    }

    public boolean isGasConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.GAS_CONSUMER);
        }
        return false;
    }

    public boolean isLiquidConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.LIQUID_CONSUMER);
        }
        return false;
    }

    public boolean isLiquidProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.machineType().contains(ONIIMachine.MachineType.LIQUID_PRODUCER);
        }
        return false;
    }

    public boolean hasProgress() {
        return getTileEntity() instanceof ONIIHasProgress;
    }

    public int getProducingPower() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.producingPower();
        }
        return 0;
    }

    public int getConsumingPower() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.consumingPower();
        }
        return 0;
    }

    public GasStack getProducingGas() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.producingGas();
        }
        return GasStack.EMPTY;
    }

    public GasStack getConsumingGas() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.consumingGas();
        }
        return GasStack.EMPTY;
    }

    public FluidStack getProducingLiquid() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.producingLiquid();
        }
        return FluidStack.EMPTY;
    }

    public FluidStack getConsumingLiquid() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.consumingLiquid();
        }
        return FluidStack.EMPTY;
    }

    public class ONIModSlotHandler extends SlotItemHandler {
        public ONIModSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isEnabled() {
            return ONIBaseContainer.this.isModTabOpen();
        }
    }

    public static class ONIMachineSlotHandler extends SlotItemHandler {
        public ONIMachineSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return getItemHandler().isItemValid(this.getSlotIndex(), stack);
        }
    }
}
