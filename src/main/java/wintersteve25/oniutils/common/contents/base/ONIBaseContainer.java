package wintersteve25.oniutils.common.contents.base;

import mekanism.api.chemical.gas.GasStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import wintersteve25.oniutils.api.*;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.IPlasma;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.utils.SlotArrangement;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public abstract class ONIBaseContainer extends AbstractContainerMenu {

    protected ONIBaseTE tileEntity;
    protected Player playerEntity;
    protected IItemHandler playerInventory;
    private boolean isModTabOpen = false;

    protected ONIBaseContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, MenuType container) {
        super(container, windowId);

        tileEntity = (ONIBaseTE) (world.getBlockEntity(pos));
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            int startPlayerInvIndex = getInvSize() + getModSlotAmount();
            int startPlayerHBIndex = getInvSize() + getModSlotAmount() + 27;
            int endPlayerInvIndex = slots.size();
            int startMachineIndex = 0;
            int startModSlotIndex = getInvSize();

            if (slot instanceof ONIMachineSlotHandler || slot instanceof ONIModSlotHandler) {
                if (!this.moveItemStackTo(stack, startPlayerInvIndex, endPlayerInvIndex, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (itemstack.getItem() instanceof ONIModificationItem) {
                    if (!this.moveItemStackTo(stack, startModSlotIndex, startPlayerInvIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickCraft(stack, itemstack);
                }
                if (index >= startPlayerHBIndex) {
                    if (!this.moveItemStackTo(stack, startMachineIndex, startPlayerHBIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (index >= startPlayerInvIndex && index < endPlayerInvIndex) {
                    if (!this.moveItemStackTo(stack, startMachineIndex, startPlayerInvIndex, false)) {
                        if (!this.moveItemStackTo(stack, startPlayerHBIndex, endPlayerInvIndex, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player p_75145_1_) {
        if (tileEntity == null) {
            return false;
        }

        if (tileEntity.getLevel() == null) {
            return false;
        }

        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, tileEntity.getLevel().getBlockState(tileEntity.getBlockPos()).getBlock());
    }

    protected void trackPower() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getPower() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(ONICapabilities.PLASMA).ifPresent(h -> {
                    int energyStored = h.getPower() & 0xffff0000;
                    h.setPower(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getPower() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(ONICapabilities.PLASMA).ifPresent(h -> {
                    int energyStored = h.getPower() & 0x0000ffff;
                    h.setPower(energyStored | (value << 16));
                });
            }
        });
    }

    protected void trackProgress() {
        if (tileEntity instanceof ONIIHasProgress) {
            ONIIHasProgress hasProgress = (ONIIHasProgress) tileEntity;
            addDataSlot(new DataSlot() {
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
            addDataSlot(new DataSlot() {
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
            addDataSlot(new DataSlot() {
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
            addDataSlot(new DataSlot() {
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
            addDataSlot(new DataSlot() {
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
            addDataSlot(new DataSlot() {
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
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getPowerCapacity() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(ONICapabilities.PLASMA).ifPresent(h -> {
                    int capacityStored = h.getCapacity() & 0xffff0000;
                    h.setCapacity(capacityStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getPowerCapacity() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(ONICapabilities.PLASMA).ifPresent(h -> {
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
        return tileEntity.getCapability(ONICapabilities.PLASMA).map(IPlasma::getPower).orElse(0);
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
        return tileEntity.getCapability(ONICapabilities.PLASMA).map(IPlasma::getCapacity).orElse(0);
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

    public BiPredicate<ItemStack, Integer> validItems() {
        if (tileEntity instanceof ONIIHasValidItems) {
            ONIIHasValidItems hasValidItems = (ONIIHasValidItems) tileEntity;
            return hasValidItems.validItemsPredicate();
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

    protected void addMachineSlot(IItemHandler itemHandler, int index, SlotArrangement tuple) {
        addSlot(new ONIMachineSlotHandler(itemHandler, index, tuple.getPixelX(), tuple.getPixelY()));
    }

    public boolean hasPower() {
        return isPowerConsumer() || isPowerProducer();
    }

    public boolean isPowerProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isPowerProducer();
        }
        return false;
    }

    public boolean isPowerConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isPowerConsumer();
        }
        return false;
    }

    public boolean isGasProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isGasProducer();
        }
        return false;
    }

    public boolean isGasConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isGasConsumer();
        }
        return false;
    }

    public boolean isLiquidConsumer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isLiquidConsumer();
        }
        return false;
    }

    public boolean isLiquidProducer() {
        if (getTileEntity() instanceof ONIIMachine) {
            ONIIMachine machine = (ONIIMachine) getTileEntity();
            return machine.isLiquidProducer();
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
        public boolean isActive() {
            return ONIBaseContainer.this.isModTabOpen();
        }
    }

    public static class ONIMachineSlotHandler extends SlotItemHandler {
        public ONIMachineSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return getItemHandler().isItemValid(this.getSlotIndex(), stack);
        }
    }
}
