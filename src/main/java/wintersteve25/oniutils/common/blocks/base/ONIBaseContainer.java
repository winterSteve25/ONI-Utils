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
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasProgress;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasValidItems;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIModifiable;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIWorkable;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.init.ONIBlocks;

import java.util.List;

public class ONIBaseContainer extends Container {

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
            trackInt(new IntReferenceHolder() {
                @Override
                public int get() {
                    return (getWorking() >> 16) & 0xffff;
                }

                @Override
                public void set(int value) {
                    int workingStored = getWorking() & 0x0000ffff;
                    int cache = workingStored | (value << 16);

                    workable.setWorking(cache == 1);
                }
            });
        } else {
            throw new UnsupportedOperationException("Trying to track total progress on a machine that does not support progress!");
        }
    }

    protected void trackPowerCapacity() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getCapacity() & 0xffff;
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
                return (getCapacity() >> 16) & 0xffff;
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

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if (slot.getHasStack()) {
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
        throw new UnsupportedOperationException("trying to get progress on an tile that does not support progress");
    }

    public int getWorking() {
        if (tileEntity instanceof ONIIWorkable) {
            ONIIWorkable workable = (ONIIWorkable) tileEntity;
            return workable.getWorking() ? 1 : 0;
        }
        throw new UnsupportedOperationException("trying to get progress on an tile that does not support progress");
    }

    public int getCapacity() {
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

    public List<Item> validItems() {
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

    public void addModificationSlots(IItemHandler itemHandler) {
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

    public class ONIModSlotHandler extends SlotItemHandler {
        public ONIModSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isEnabled() {
            return ONIBaseContainer.this.isModTabOpen();
        }
    }
}
